package org.example.librarySystem;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private String type;
    private boolean availability;
    private List<String> borrowHistory;

    public Book(String isbn, String title, String author, String genre, String type, boolean availability) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.type = type;
        this.availability = availability;
        this.borrowHistory = new ArrayList<>();
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getGenre() {
        return genre;
    }
    public String getType() {
        return type;
    }
    public boolean isAvailable() {
        return availability;
    }
    public List<String> getBorrowingHistory() {
        return borrowHistory;
    }
}