package org.example.librarySystem;

import java.time.LocalDate;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

interface LibraryOperations {
    void addBook(Book book);
    //void removeBook(String isbn);
    void addPatron(Patron patron);
    //void removePatron(int id);
}

public class Library implements LibraryOperations {
    private final MongoClient client = MongoClients.create("mongodb+srv://jaguayo8:q9NPAQHk97BkfD7@biblioconnectsystem.gorl0cv.mongodb.net/?retryWrites=true&w=majority&appName=biblioConnectSystem");
    private final MongoDatabase db = client.getDatabase("biblioConnectSystem");
    private final MongoCollection<Document> bookCol = db.getCollection("books");
    private final MongoCollection<Document> patronCol = db.getCollection("patrons");
    private final MongoCollection<Document> transCol = db.getCollection("transactions");

    // Add book to the library
    public void addBook(Book book) {
        boolean cont = false;
        try {
            Document bookDoc = new Document("_id", bookCol.countDocuments() + 1)
                    .append("isbn", book.getIsbn())
                    .append("title", book.getTitle())
                    .append("author", book.getAuthor())
                    .append("genre", book.getGenre().toLowerCase())
                    .append("type", book.getType())
                    .append("availability", book.isAvailable())
                    .append("borrowHistory", book.getBorrowingHistory());
            bookCol.insertOne(bookDoc);
            cont = true;
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        } finally {
            if (cont) {
                System.out.println("----------------------------------------");
                System.out.println("\tThe book was added successfully!");
                System.out.println("----------------------------------------");
            }
        }
    }

    // Add patron to the library
    public void addPatron(Patron patron) {
        boolean cont = false;
        try {
            Document patronDoc = new Document("_id", patronCol.countDocuments() + 1)
                    .append("name", patron.getName())
                    .append("password", patron.getPassword())
                    .append("contactInfo", patron.getContactInfo())
                    .append("type", patron.getType())
                    .append("id", patron.getId())
                    .append("borrowHistory", patron.getBorrowingHistory())
                    .append("favBooks", patron.getFavBooks())
                    .append("favGenres", patron.getFavGenres())
                    .append("friendsList", patron.getFriendsList());
            patronCol.insertOne(patronDoc);
            cont = true;
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        } finally {
            if (cont) {
                System.out.println("-------------------------------------------");
                System.out.println("\tPatron was registered successfully!");
                System.out.println("-------------------------------------------");
                System.out.println("Your Patron ID is: " + patronCol.countDocuments());
                System.out.println("-------------------------------------------");
            }
        }
    }

    // Borrow book
    public void borrowBook(int patronId, String isbn) {
        String bookTitle = "";
        String patronName = "";
        boolean cont = false;
        try {
            Document patronDoc = patronCol.find(Filters.eq("_id", patronId)).first();
            Document bookDoc = bookCol.find(Filters.eq("isbn", isbn)).first();
            if (patronDoc == null || bookDoc == null) {
                System.err.println("Error: Patron ID or book ISBN not found!");
                return;
            }

            boolean availability = bookDoc.getBoolean("availability");
            if (!availability) {
                System.err.println("Error: Book is currently unavailable");
                return;
            }

            String bookType = bookDoc.getString("type");
            String patronType = patronDoc.getString("type");
            if (patronType.equalsIgnoreCase("student") && bookType.equalsIgnoreCase("fiction")) {
                System.out.println("-----------------------------------------------");
                System.out.println("Students can only check out non-fiction books.");
                System.out.println("-----------------------------------------------");
                return;
            }

            bookTitle = bookDoc.getString("title");
            patronName = patronDoc.getString("name");
            Document transDoc = new Document("_id", transCol.countDocuments() + 1)
                    .append("patronID", patronId)
                    .append("bookISBN", isbn)
                    .append("returned", false)
                    .append("borrowDate", LocalDate.now());
            transCol.insertOne(transDoc);

            //Update Book
            Document bookFilter = new Document("isbn", isbn);
            Document updateBook = new Document("$set", new Document("availability", false));
            bookCol.updateOne(bookDoc, updateBook);
            Document updateBookList = new Document("$push", new Document("borrowHistory", patronName));
            bookCol.updateOne(bookFilter, updateBookList);

            //Update Patron
            Document updatePatronList = new Document("$push", new Document("borrowHistory", bookTitle));
            patronCol.updateOne(patronDoc, updatePatronList);
            cont = true;
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        } finally {
            if (cont) {
                System.out.println("----------------------------------------------");
                System.out.println("\tThe book was checked out successfully!");
                System.out.println("----------------------------------------------");
                System.out.println("The book, " + bookTitle + ", has been borrowed by patron, " + patronName);
                System.out.println("Your Transaction ID is: " + transCol.countDocuments());
                System.out.println("----------------------------------------------");
            }
        }
    }

    // Return book
    public void returnBook(int id) {
        String bookTitle = "";
        String patronName = "";
        boolean cont = false;
        try {
            Document transDoc = transCol.find(Filters.eq("_id", id)).first();
            if (transDoc == null) {
                System.err.println("Error: Transaction ID not found!");
                return;
            }

            boolean returned = transDoc.getBoolean("returned");
            if (returned) {
                System.err.println("Error: Book has already been returned");
                return;
            }

            //Update Transaction
            Document transFilter = new Document("_id", id);
            Document updateTrans = new Document("$set", new Document("returned", true));
            transCol.updateOne(transFilter, updateTrans);
            updateTrans = new Document("$set", new Document("returnDate", LocalDate.now()));
            transCol.updateOne(transFilter, updateTrans);

            //Update Book
            String isbn = transDoc.getString("bookISBN");
            Document bookFilter = new Document("isbn", isbn);
            Document updateBook = new Document("$set", new Document("availability", true));
            bookCol.updateOne(bookFilter, updateBook);

            int patronId = transDoc.getInteger("patronID");
            Document patronDoc = patronCol.find(Filters.eq("_id", patronId)).first();
            Document bookDoc = bookCol.find(Filters.eq("isbn", isbn)).first();
            patronName = patronDoc != null ? patronDoc.getString("name") : "[name]";
            bookTitle = bookDoc != null ? bookDoc.getString("title") : "[title]";
            cont = true;
        } catch (MongoException e) {
            System.err.println("MongoDB Error: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return;
        } finally {
            if (cont) {
                System.out.println("-----------------------------------------------");
                System.out.println("\t  The book was returned successfully!");
                System.out.println("-----------------------------------------------");
                System.out.println("The book, " + bookTitle + ", has been returned by patron, " + patronName);
                System.out.println("-----------------------------------------------");
            }
        }
    }
}