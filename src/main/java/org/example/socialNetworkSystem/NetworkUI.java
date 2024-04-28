package org.example.socialNetworkSystem;
import java.util.Scanner;

public class NetworkUI {
    private final Network network;
    private final Scanner scanner;
    private boolean signedIn;

    public NetworkUI() {
        this.network = new Network();
        this.scanner = new Scanner(System.in);
        this.signedIn = false;
    }

    public void displayMenu() {
        //Title Text
        System.out.println("\nWelcome to the");
        System.out.println(" __         __     ______   ______     ______     ______     ______     __  __    ");
        System.out.println("/\\ \\       /\\ \\   /\\__  _\\ /\\  ___\\   /\\  == \\   /\\  == \\   /\\  == \\   /\\ \\_\\ \\   ");
        System.out.println("\\ \\ \\____  \\ \\ \\  \\/_/\\ \\/ \\ \\  __\\   \\ \\  __<   \\ \\  __ \\  \\ \\  __<   \\ \\____ \\  ");
        System.out.println(" \\ \\_____\\  \\ \\_\\    \\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\/\\_____\\ ");
        System.out.println("  \\/_____/   \\/_/     \\/_/   \\/_____/   \\/_/ /_/   \\/_/\\/_/   \\/_/\\/_/   \\/_____/  ");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Social Network! v1.0\n");
        //Menu Text
        System.out.println("Select an Option:");
        //System.out.println("0. Debug");
        System.out.println(" 1. User Profiles");
        System.out.println(" 2. Groups and Discussions");
        System.out.println(" 3. Events and Meetups");
        if (!signedIn) {
            System.out.println(" 4. Sign-In");
        } else {
            System.out.println(" 4. Sign-Out");
        }
        System.out.println(" 5. Main Menu");
    }

    public void handleUserInput() {
        boolean exit = false;
        displayMenu();
        while (!exit) {
            System.out.print("\nEnter your choice: ");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    break;
                case 1:
                    clearScreen();
                    userProfiles();
                    break;
                case 2:
                    clearScreen();
                    groupsNDiscussions();
                    break;
                case 3:
                    clearScreen();
                    eventsNMeetups();
                    break;
                case 4:
                    clearScreen();
                    if (!signedIn) {
                        signIn();
                    } else {
                        signOut();
                    }
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        //scanner.close();
        clearScreen();
    }

    private void userProfiles() {
        String patronId = "";
        if (!signedIn) {
            //Title Text
            System.out.println("--------------------------");
            System.out.println("\t User Profiles");
            System.out.println("--------------------------");
            //Menu Text
            System.out.print("\nEnter Patron ID: ");
            patronId = scanner.nextLine();
            clearScreen();
            network.userProfile(patronId);
        } else {
            clearScreen();
            network.userProfile(patronId);
        }
        //Exit Text
        handleUserInput();
    }

    private void groupsNDiscussions() {
        network.groupsNDiscussions();
        //Exit Text
        handleUserInput();
    }

    private void eventsNMeetups() {
        network.eventsNMeetups();
        //Exit Text
        handleUserInput();
    }

    private void signOut() {
        System.out.println("-------------------------------");
        System.out.println("\tSuccessfully Signed Out");
        System.out.println("-------------------------------");
        signedIn = network.signOut();
        //Exit Text
        handleUserInput();
        return;
    }

    private void signIn() {
        System.out.print("\nEnter Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        signedIn = network.signIn(patronId, password);
        //Exit Text
        handleUserInput();
        return;
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
