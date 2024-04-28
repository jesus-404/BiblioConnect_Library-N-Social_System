package org.example.librarySystem;
import java.util.ArrayList;
import java.util.List;

public class Patron {
    private String name;
    private String password;
    private String contactInfo;
    private String type;
    private int id;
    private List<String> borrowHistory;
    private List<String> favBooks;
    private List<String> favGenres;
    private List<String> friendsList;



    public Patron(String name, String password, String contactInfo, String type, int id) {
        this.name = name;
        this.password = password;
        this.contactInfo = contactInfo;
        this.type = type;
        this.id = id;
        this.borrowHistory = new ArrayList<>();
        this.favBooks = new ArrayList<>();
        this.favGenres = new ArrayList<>();
        this.friendsList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public String getType() {
        return type;
    }
    public List<String> getBorrowingHistory() {
        return borrowHistory;
    }
    public List<String> getFavBooks() {
        return favBooks;
    }
    public List<String> getFavGenres() {
        return favGenres;
    }
    public List<String> getFriendsList() {
        return friendsList;
    }
}