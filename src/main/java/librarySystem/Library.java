package librarySystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface LibraryOperations {
    void addBook(Book book);
    //void removeBook(String isbn);
    void addPatron(String name, String contactInfo);
    //void removePatron(int id);
}

public class Library implements LibraryOperations {
    // Main Classes
    private Map<String, Book> books;
    private Map<Integer, Patron> patrons;
    private List<Transaction> transactions;
    private int transactionIdCounter;
    private int patronIdCounter;

    // Sub-classes
    private List<Author> authors;
    private List<Genre> genres;
    private Map<Integer, StudentPatron> studentPatrons;
    private Map<Integer, FacultyPatron> facultyPatrons;

    public Library() {
        this.books = new HashMap<>();
        this.patrons = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.transactionIdCounter = 1;
        this.patronIdCounter = 1;

        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.studentPatrons = new HashMap<>();
        this.facultyPatrons = new HashMap<>();
    }

    // Add book to the library
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    // Add patron to the library
    public void addPatron(String name, String contactInfo) {
        patrons.put(patronIdCounter, new Patron(patronIdCounter, name, contactInfo));
        System.out.println("Your Patron ID is: " + patronIdCounter++);
    }

    public void addStudentPatron(String name, String contactInfo, int studentId) {
        studentPatrons.put(patronIdCounter, new StudentPatron(patronIdCounter, name, contactInfo, studentId));
        addPatron(name, contactInfo);
    }

    public void addFacultyPatron(String name, String contactInfo, int facultyId) {
        facultyPatrons.put(patronIdCounter, new FacultyPatron(patronIdCounter, name, contactInfo, facultyId));
        addPatron(name, contactInfo);
    }

    // Borrow book
    public void borrowBook(int patronId, String isbn) {
        Patron patron = patrons.get(patronId);
        Book book = books.get(isbn);

        Transaction transaction = new Transaction(transactionIdCounter, patron, book);
        transactions.add(transaction);
        book.setAvailability(false);
        patron.addBookToHistory(book);
        System.out.println("The book, " + book.getTitle() + ", has been borrowed by patron, " + patron.getName());
        System.out.println("Your Transaction ID is: " + transactionIdCounter++);
    }

    // Return book
    public void returnBook(int id) {
        Transaction transaction = findTransactionById(id);
        Book book = transaction.getBook();
        Patron patron = transaction.getPatron();

        transaction.markAsReturned();
        book.setAvailability(true);
        System.out.println("The book, " + book.getTitle() + ", has been returned by patron, " + patron.getName());
    }

    // Additional add functions

    public void addAuthor(String author) {
        authors.add(new Author(author));
    }

    public void addGenre(String genre) {
        genres.add(new Genre(genre));
    }

    // Additional search functions
    public Author findAuthorByName(String name) {
        for (Author author : authors) {
            if (author.getName().equals(name)) {
                return author;
            }
        }
        return null;
    }

    public Genre findGenreByName(String name) {
        for (Genre genre : genres) {
            if (genre.getName().equals(name)) {
                return genre;
            }
        }
        return null;
    }

    public Patron findPatronById(int id) {
        Patron patron = patrons.get(id);
        if (patron != null) {
            return patron;
        }

        return null;
    }

    public Patron findStudentPatronById(int id) {
        StudentPatron patron = studentPatrons.get(id);
        if (patron != null) {
            return patron;
        }

        return null;
    }

    public Book findBookByISBN(String isbn) {
        Book book = books.get(isbn);
        if (book != null) {
            return book;
        }

        return null;
    }

    public Transaction findTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == id) {
                return transaction;
            }
        }
        return null;
    }
}