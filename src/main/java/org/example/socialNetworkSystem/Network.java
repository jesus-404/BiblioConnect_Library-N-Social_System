package org.example.socialNetworkSystem;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Network {
    private final Scanner scanner;
    private final MongoClient client = MongoClients.create("mongodb+srv://jaguayo8:q9NPAQHk97BkfD7@biblioconnectsystem.gorl0cv.mongodb.net/?retryWrites=true&w=majority&appName=biblioConnectSystem");
    private final MongoDatabase db = client.getDatabase("biblioConnectSystem");
    private final MongoCollection<Document> patronCol = db.getCollection("patrons");
    private final MongoCollection<Document> groupsCol = db.getCollection("groups");
    private final MongoCollection<Document> discussionsCol = db.getCollection("discussions");
    private final MongoCollection<Document> eventsCol = db.getCollection("events");


    private Document signedInPatronDoc;
    private boolean signedIn;

    public Network() {
        this.scanner = new Scanner(System.in);
        this.signedInPatronDoc = new Document();
        this.signedIn = false;
    }

    public void userProfile(String patronId) {
        try {
            Document patronDoc;
            if (!signedIn) {
                patronDoc = patronCol.find(Filters.eq("_id", Integer.parseInt(patronId))).first();
                if (patronDoc == null) {
                    System.err.println("Error: Incorrect ID");
                    return;
                }
            } else {
                patronDoc = signedInPatronDoc;
            }
            String name = patronDoc.getString("name");
            boolean contLoop = true;
            boolean exit = false;
            while (!exit) {
                //Title Text
                System.out.println("-------------------------");
                System.out.println(name + "'s Profile");
                System.out.println("-------------------------");
                //Menu Text
                System.out.println("Select an Option:");
                System.out.println(" 1. Favorite Book");
                System.out.println(" 2. Favorite Genres");
                System.out.println(" 3. Friends List");
                System.out.println(" 4. Go Back");
                contLoop = true;
                //Input Text
                while (contLoop) {
                    System.out.print("\nEnter your choice: ");
                    int choice = getIntInput();
                    scanner.nextLine();
                    System.out.print("");

                    switch (choice) {
                        case 1:
                            List<String> favbooks = patronDoc.getList("favBooks", String.class);
                            if (!favbooks.isEmpty()) {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("Favorite Books List:");
                                for (String book : favbooks) {
                                    System.out.println("\t- " + book);
                                }
                                System.out.println("------------------------------------------------");
                            } else {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("\t\t Favorite books list is empty");
                                System.out.println("------------------------------------------------");
                            }
                            if (signedIn) {
                                System.out.print("\nEnter your favorite book (Enter to Skip): ");
                                String newFavBook = scanner.nextLine();
                                if (!newFavBook.isEmpty()) {
                                    Document updateFavBooks = new Document("$push", new Document("favBooks", newFavBook));
                                    patronCol.updateOne(patronDoc, updateFavBooks);
                                }
                                clearScreen();
                                System.out.println("----------------------------------------");
                                if (!newFavBook.isEmpty()) {
                                    System.out.println("\tThe book was added successfully!");
                                } else {
                                    System.out.println("\t No book was added to the list");
                                }
                                System.out.println("----------------------------------------");
                            }
                            contLoop = false;
                            break;
                        case 2:
                            List<String> favGenres = patronDoc.getList("favGenres", String.class);
                            if (!favGenres.isEmpty()) {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("Favorite Genres List:");
                                for (String genre : favGenres) {
                                    System.out.println("\t- " + genre);
                                }
                                System.out.println("------------------------------------------------");
                            } else {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("\t\tFavorite genres list is empty");
                                System.out.println("------------------------------------------------");
                            }
                            if (signedIn) {
                                System.out.print("\nEnter your favorite genre (Enter to Skip): ");
                                String newFavGenre = scanner.nextLine();
                                if (!newFavGenre.isEmpty()) {
                                    Document updateFavGenres = new Document("$push", new Document("favGenres", newFavGenre));
                                    patronCol.updateOne(patronDoc, updateFavGenres);
                                }
                                clearScreen();
                                System.out.println("-----------------------------------------");
                                if (!newFavGenre.isEmpty()) {
                                    System.out.println("\tThe genre was added successfully!");
                                } else {
                                    System.out.println("\t No genre was added to the list");
                                }
                                System.out.println("-----------------------------------------");

                            }
                            contLoop = false;
                            break;
                        case 3:
                            List<String> friendsList = patronDoc.getList("friendsList", String.class);
                            if (!friendsList.isEmpty()) {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("Friends List:");
                                for (String friend : friendsList) {
                                    System.out.println("\t- " + friend);
                                }
                                System.out.println("------------------------------------------------");
                            } else {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("\t\t\t Friend list is empty");
                                System.out.println("------------------------------------------------");
                            }
                            if (signedIn) {
                                System.out.print("\nEnter your friend's name (Enter to Skip): ");
                                String newFriend = scanner.nextLine();
                                if (!newFriend.isEmpty()) {
                                    Document updateFriendsList = new Document("$push", new Document("friendsList", newFriend));
                                    patronCol.updateOne(patronDoc, updateFriendsList);
                                }
                                clearScreen();
                                System.out.println("-------------------------------------------");
                                if (!newFriend.isEmpty()) {
                                    System.out.println("\tYour friend was added successfully!");
                                } else {
                                    System.out.println("\t  No friend was added to the list");
                                }
                                System.out.println("-------------------------------------------");

                            }
                            contLoop = false;
                            break;
                        case 4:
                            contLoop = false;
                            exit = true;
                            clearScreen();
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }

    public void groupsNDiscussions() {
        try {
            boolean contLoop = true;
            boolean exit = false;
            String groupName = "";
            while (!exit) {
                //Title Text
                System.out.println("------------------------------");
                System.out.println("\tGroups and Discussions");
                System.out.println("------------------------------");
                //Menu Text
                System.out.println("Select an Option:");
                System.out.println(" 1. View Groups");
                System.out.println(" 2. View Discussions");
                System.out.println(" 3. Go Back");
                contLoop = true;
                //Input Text
                while (contLoop) {
                    System.out.print("\nEnter your choice: ");
                    int choice = getIntInput();
                    scanner.nextLine();
                    System.out.print("");

                    switch (choice) {
                        case 1:
                            //Display all groups
                            long numOfGroups = groupsCol.countDocuments();
                            if (numOfGroups > 0) {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("All Groups:");
                                for (Document group : groupsCol.find()) {
                                    String name = group.getString("name");
                                    System.out.println("\t- " + name);
                                }
                                System.out.println("------------------------------------------------");
                                //Check specific group
                                System.out.print("Enter group name to view (Enter to Skip): ");
                                groupName = scanner.nextLine();
                                if (!groupName.isEmpty()) {
                                    //Enter specific group
                                    Document groupDoc = groupsCol.find(Filters.eq("name", groupName)).first();
                                    if (groupDoc == null) {
                                        clearScreen();
                                        System.err.println("Error: Group name not found");
                                        return;
                                    }
                                    List<String> groupMembers = groupDoc.getList("members", String.class);
                                    boolean inGroup = false;
                                    //Display Group Name
                                    clearScreen();
                                    System.out.println("------------------------------------------------");
                                    System.out.println(groupName);
                                    System.out.println("------------------------------------------------");
                                    //Display Group Members
                                    if (!groupMembers.isEmpty()) {
                                        System.out.println("All Members:");
                                        for (String member : groupMembers) {
                                            System.out.println("\t- " + member);
                                            if (member.equals(signedInPatronDoc.getString("name"))) {
                                                inGroup = true;
                                            }
                                        }
                                        System.out.println("------------------------------------------------");
                                    } else {
                                        System.out.println("Group is empty");
                                        System.out.println("------------------------------------------------");
                                    }
                                    if (signedIn) {
                                        if (!inGroup) {
                                            System.out.print("Join Group? (True/False): ");
                                            String join = scanner.nextLine();
                                            if (join.equalsIgnoreCase("true") || join.equalsIgnoreCase("t")) {
                                                String name = signedInPatronDoc.getString("name");
                                                Document newMember = new Document("$push", new Document("members", name));
                                                groupsCol.updateOne(groupDoc, newMember);
                                                clearScreen();
                                                System.out.println("Joined group successfully!");
                                            } else {
                                                clearScreen();
                                                System.out.println("No group was joined");
                                            }
                                        } else {
                                            System.out.print("Leave Group? (True/False): ");
                                            String leave = scanner.nextLine();
                                            if (leave.equalsIgnoreCase("true") || leave.equalsIgnoreCase("t")) {
                                                String name = signedInPatronDoc.getString("name");
                                                Document newMember = new Document("$pull", new Document("members", name));
                                                groupsCol.updateOne(groupDoc, newMember);
                                                clearScreen();
                                                System.out.println("Left group successfully!");
                                            } else {
                                                clearScreen();
                                                System.out.println("No group was left");
                                            }
                                        }
                                    } else {
                                        System.out.println("Sign In to join group");
                                    }
                                } else {
                                    clearScreen();
                                }
                            } else {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("\t\t\t\tNo Groups Found");
                                System.out.println("------------------------------------------------");
                            }
                            if (signedIn && groupName.isEmpty()) {
                                System.out.print("Create a Group? (True/False): ");
                                String create = scanner.nextLine();
                                if (create.equalsIgnoreCase("true") || create.equalsIgnoreCase("t")) {
                                    //Create new group
                                    System.out.print("\nEnter Group Name: ");
                                    String newGroupName = scanner.nextLine();
                                    List<String> members = new ArrayList<>();
                                    members.add(signedInPatronDoc.getString("name"));
                                    Document groupDoc = new Document("_id", groupsCol.countDocuments() + 1)
                                            .append("name", newGroupName)
                                            .append("members", members);
                                    groupsCol.insertOne(groupDoc);
                                    clearScreen();
                                    System.out.println("Created group successfully!");
                                } else {
                                    clearScreen();
                                    System.out.println("No group was created");
                                }
                            } else {
                                System.out.println("Sign In to create a group");
                            }
                            contLoop = false;
                            break;
                        case 2:
                            //Display all discussions
                            long numOfDis = discussionsCol.countDocuments();
                            String disTopic = "";
                            if (numOfDis > 0) {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("All Discussions:");
                                for (Document dis : discussionsCol.find()) {
                                    String topic = dis.getString("topic");
                                    System.out.println("\t- " + topic);
                                }
                                System.out.println("------------------------------------------------");
                                //Check specific discussion
                                System.out.print("Enter discussion topic to view (Enter to Skip): ");
                                disTopic = scanner.nextLine();
                                if (!disTopic.isEmpty()) {
                                    //Enter specific group
                                    Document disDoc = discussionsCol.find(Filters.eq("topic", disTopic)).first();
                                    if (disDoc == null) {
                                        clearScreen();
                                        System.err.println("Error: Discussion topic not found");
                                        return;
                                    }
                                    List<String> disComments = disDoc.getList("comments", String.class);
                                    //Display Group Name
                                    clearScreen();
                                    System.out.println("------------------------------------------------");
                                    System.out.println(disTopic);
                                    System.out.println("------------------------------------------------");
                                    //Display Group Members
                                    if (!disComments.isEmpty()) {
                                        System.out.println("All comments:");
                                        for (String comment : disComments) {
                                            System.out.println(comment);
                                        }
                                        System.out.println("------------------------------------------------");
                                    } else {
                                        System.out.println("No comments found");
                                        System.out.println("------------------------------------------------");
                                    }
                                    if (signedIn) {
                                        System.out.print("Leave a comment? (True/False): ");
                                        String post = scanner.nextLine();
                                        if (post.equalsIgnoreCase("true") || post.equalsIgnoreCase("t")) {
                                            //Create comment in discussion
                                            System.out.print("\nEnter comment: ");
                                            String comment = scanner.nextLine();
                                            String name = signedInPatronDoc.getString("name");
                                            String fullComment = name + " - " + comment;
                                            Document newComment = new Document("$push", new Document("comments", fullComment));
                                            discussionsCol.updateOne(disDoc, newComment);
                                            clearScreen();
                                            System.out.println("Post a new comment successfully!");
                                        } else {
                                            clearScreen();
                                            System.out.println("No new comment was left");
                                        }
                                    } else {
                                        System.out.println("Sign In to post a comment");
                                    }
                                } else {
                                    clearScreen();
                                }
                            } else {
                                if (!signedIn) {clearScreen();}
                                System.out.println("\n------------------------------------------------");
                                System.out.println("\t\t\t No Discussions Found");
                                System.out.println("------------------------------------------------");
                            }
                            if (signedIn && disTopic.isEmpty()) {
                                System.out.print("Create a new discussion? (True/False): ");
                                String create = scanner.nextLine();
                                if (create.equalsIgnoreCase("true") || create.equalsIgnoreCase("t")) {
                                    //Create a new discussion
                                    System.out.print("\nEnter a Discussion Topic: ");
                                    String newDisTopic = scanner.nextLine();
                                    List<String> comments = new ArrayList<>();
                                    Document groupDoc = new Document("_id", discussionsCol.countDocuments() + 1)
                                            .append("topic", newDisTopic)
                                            .append("comments", comments);
                                    discussionsCol.insertOne(groupDoc);
                                    clearScreen();
                                    System.out.println("Created a new discussion successfully!");
                                } else {
                                    clearScreen();
                                    System.out.println("No new discussion was created");
                                }
                            } else {
                                System.out.println("Sign In to create a new discussion");
                            }
                            contLoop = false;
                            break;
                        case 3:
                            contLoop = false;
                            exit = true;
                            clearScreen();
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }
    }

    public void eventsNMeetups() {
        //Display all events
        long numOfEvents = eventsCol.countDocuments();
        String eventName = "";
        if (numOfEvents > 0) {
            //Title Text
            System.out.println("------------------------------------------------");
            System.out.println("\t\t\t  Events and Meetings");
            System.out.println("------------------------------------------------");
            System.out.println("All Events:");
            for (Document events : eventsCol.find()) {
                String name = events.getString("name");
                String date = events.getString("date");
                String time = events.getString("time");
                String location = events.getString("location");
                String host = events.getString("host");
                System.out.println("\t- " + host + ": " + name + " @ " + location + ", during " + date + ", " + time);
            }
            System.out.println("------------------------------------------------");
            //Check specific event
            System.out.print("Enter event name to view (Enter to Skip): ");
            eventName = scanner.nextLine();
            if (!eventName.isEmpty()) {
                //Enter specific event
                Document eventDoc = eventsCol.find(Filters.eq("name", eventName)).first();
                if (eventDoc == null) {
                    clearScreen();
                    System.err.println("Error: Event name not found");
                    return;
                }
                List<String> eventMembers = eventDoc.getList("members", String.class);
                boolean inGroup = false;
                //Display Group Name
                clearScreen();
                System.out.println("------------------------------------------------");
                System.out.println(eventName);
                System.out.println("------------------------------------------------");
                //Display Group Members
                if (!eventMembers.isEmpty()) {
                    System.out.println("All Members:");
                    for (String member : eventMembers) {
                        System.out.println("\t- " + member);
                        if (member.equals(signedInPatronDoc.getString("name"))) {
                            inGroup = true;
                        }
                    }
                    System.out.println("------------------------------------------------");
                } else {
                    System.out.println("Event is empty");
                    System.out.println("------------------------------------------------");
                }
                if (signedIn) {
                    if (!inGroup) {
                        System.out.print("Join Event? (True/False): ");
                        String join = scanner.nextLine();
                        if (join.equalsIgnoreCase("true") || join.equalsIgnoreCase("t")) {
                            String name = signedInPatronDoc.getString("name");
                            Document newMember = new Document("$push", new Document("members", name));
                            eventsCol.updateOne(eventDoc, newMember);
                            clearScreen();
                            System.out.println("Joined event successfully!");
                        } else {
                            clearScreen();
                            System.out.println("No event was joined");
                        }
                    } else {
                        System.out.print("Leave Event? (True/False): ");
                        String leave = scanner.nextLine();
                        if (leave.equalsIgnoreCase("true") || leave.equalsIgnoreCase("t")) {
                            String name = signedInPatronDoc.getString("name");
                            Document newMember = new Document("$pull", new Document("members", name));
                            eventsCol.updateOne(eventDoc, newMember);
                            clearScreen();
                            System.out.println("Left event successfully!");
                        } else {
                            clearScreen();
                            System.out.println("No event was left");
                        }
                    }
                } else {
                    System.out.println("Sign In to join an event");
                }
            } else {
                clearScreen();
            }
        } else {
            if (!signedIn) {clearScreen();}
            System.out.println("\n------------------------------------------------");
            System.out.println("\t\t\t\tNo Events Found");
            System.out.println("------------------------------------------------");
        }
        if (signedIn && eventName.isEmpty()) {
            System.out.print("Create a new event? (True/False): ");
            String create = scanner.nextLine();
            if (create.equalsIgnoreCase("true") || create.equalsIgnoreCase("t")) {
                //Create new event
                System.out.print("\nEnter Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Date (00/00/00): ");
                String date = scanner.nextLine();
                System.out.print("Enter Time (00am pst): ");
                String time = scanner.nextLine();
                System.out.print("Enter Location: ");
                String location = scanner.nextLine();
                List<String> members = new ArrayList<>();
                Document eventDoc = new Document("_id", eventsCol.countDocuments() + 1)
                        .append("host", signedInPatronDoc.getString("name"))
                        .append("name", name)
                        .append("date", date)
                        .append("time", time)
                        .append("location", location)
                        .append("members", members);
                eventsCol.insertOne(eventDoc);
                clearScreen();
                System.out.println("Created event successfully!");
            } else {
                clearScreen();
                System.out.println("No event was created");
            }
        } else {
            System.out.println("Sign In to create an event");
        }
    }

    public boolean signIn (String patronId, String password) {
        try {
            signedInPatronDoc = patronCol.find(Filters.eq("_id", Integer.parseInt(patronId))).first();
            if (signedInPatronDoc == null) {
                System.err.println("Error: Incorrect ID");
                return false;
            }
            String patronPassword = signedInPatronDoc.getString("password");
            if (!patronPassword.equals(password)) {
                System.err.println("Error: Incorrect Password");
                return false;
            }
            //Exit Text
            clearScreen();
            String name = signedInPatronDoc.getString("name");
            System.out.println("------------------------------");
            System.out.println("\tSuccessfully Signed In");
            System.out.println("------------------------------");
            System.out.print("Hello, " + name + "!");
            signedIn = true;
            return true;
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean signOut () {
        signedInPatronDoc = new Document();
        signedIn = false;
        return false;
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
