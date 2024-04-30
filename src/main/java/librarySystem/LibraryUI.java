package librarySystem;
import java.util.Scanner;

public class LibraryUI {
    private final Library library;
    private final Scanner scanner;

    public LibraryUI() {
        this.library = new Library();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        //Title Text
        System.out.println("\nWelcome to the");
        System.out.println(" __         __     ______     ______     ______     ______     __  __    ");
        System.out.println("/\\ \\       /\\ \\   /\\  == \\   /\\  == \\   /\\  == \\   /\\  == \\   /\\ \\_\\ \\   ");
        System.out.println("\\ \\ \\____  \\ \\ \\  \\ \\  __<   \\ \\  __<   \\ \\  __ \\  \\ \\  __<   \\ \\____ \\  ");
        System.out.println(" \\ \\_____\\  \\ \\_\\  \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\/\\_____\\ ");
        System.out.println("  \\/_____/   \\/_/   \\/_____/   \\/_/ /_/   \\/_/\\/_/   \\/_/ /_/   \\/_____/  ");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t Management System! v2.0\n");
        //Menu Text
        System.out.println("Select an Option:");
        //System.out.println("0. Debug");
        System.out.println(" 1. Add Book");
        System.out.println(" 2. Register Patron");
        System.out.println(" 3. Check Out Book");
        System.out.println(" 4. Return Book");
        System.out.println(" 5. Exit");
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
                    addBook();
                    break;
                case 2:
                    clearScreen();
                    registerPatron();
                    break;
                case 3:
                    clearScreen();
                    checkOutBook();
                    break;
                case 4:
                    clearScreen();
                    returnBook();
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

    private void clearScreen() {
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

    private void addBook() {
        //Title Text
        System.out.println("------------------------");
        System.out.println("\t\tAdd Book");
        System.out.println("------------------------");
        //Menu Text
        System.out.print("\nEnter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Is this book fictional? (true/false): ");
        String type = scanner.nextLine();
        if (type.equalsIgnoreCase("true") || type.equalsIgnoreCase("t")) {
            FictionBook book = new FictionBook(isbn, title, author, genre, true);
            sortBook(book, author, genre);
        } else {
            NonFictionBook book = new NonFictionBook(isbn, title, author, genre, true);
            sortBook(book, author, genre);
        }
        //Exit Text
        clearScreen();
        System.out.println("----------------------------------------");
        System.out.println("\tThe book was added successfully!");
        System.out.println("----------------------------------------");
        handleUserInput();
    }

    private void sortBook(Book book, String author, String genre) {
        library.addBook(book);

        Author authorObj = library.findAuthorByName(author);
        if (authorObj == null) {
            library.addAuthor(author);
            authorObj = library.findAuthorByName(author);
        }
        authorObj.addBook(book);

        Genre genreObj = library.findGenreByName(genre);
        if (genreObj == null) {
            library.addGenre(genre);
            genreObj = library.findGenreByName(genre);
        }
        genreObj.addBook(book);
    }

    private void registerPatron() {
        int id = 0;
        //Title Text
        System.out.println("------------------------");
        System.out.println("\tRegister Patron");
        System.out.println("------------------------");
        //Menu Text
        System.out.print("\nEnter Patron Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patron Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Are You a Student or Faculty Patron? (student/faculty/none): ");
        String patronType = scanner.nextLine();
        if (patronType.equalsIgnoreCase("student") || patronType.equalsIgnoreCase("s")) {
            System.out.print("Enter a Student ID: ");
            id = getIntInput();
        } else if (patronType.equalsIgnoreCase("faculty") || patronType.equalsIgnoreCase("f")) {
            System.out.print("Enter a Faculty ID: ");
            id = getIntInput();
        }
        //Exit Text
        clearScreen();
        System.out.println("-------------------------------------------");
        System.out.println("\tPatron was registered successfully!");
        System.out.println("-------------------------------------------");
        if (patronType.equalsIgnoreCase("student") || patronType.equalsIgnoreCase("s")) {
            library.addStudentPatron(name, contactInfo, id);
        } else if (patronType.equalsIgnoreCase("faculty") || patronType.equalsIgnoreCase("f")) {
            library.addFacultyPatron(name, contactInfo, id);
        } else {
            library.addPatron(name, contactInfo);
        }
        System.out.println("-------------------------------------------");
        handleUserInput();
    }

    private void checkOutBook() {
        //Title Text
        System.out.println("------------------------");
        System.out.println("\t Check Out Book");
        System.out.println("------------------------");
        //Menu Text
        System.out.print("\nEnter Patron ID: ");
        int patronId = getIntInput();
        System.out.print("Enter Book ISBN: ");
        int num = getIntInput();
        String isbn = Integer.toString(num);

        if (library.findPatronById(patronId) == null || library.findBookByISBN(isbn) == null) {
            System.out.println("Invalid patron ID or book ISBN.");
            handleUserInput();
        }

        if (!library.findBookByISBN(isbn).isAvailable()) {
            System.out.println("Book is currently unavailable");
            handleUserInput();
        }

        if ((library.findStudentPatronById(patronId) != null) && (library.findBookByISBN(isbn) instanceof FictionBook)) {
            System.out.println("Students can only check out non-fiction books.");
            handleUserInput();
        }
        //Exit Text
        clearScreen();
        System.out.println("----------------------------------------------");
        System.out.println("\tThe book was checked out successfully!");
        System.out.println("----------------------------------------------");
        library.borrowBook(patronId, isbn);
        System.out.println("----------------------------------------------");
        handleUserInput();
    }

    private void returnBook() {
        //Title Text
        System.out.println("------------------------");
        System.out.println("\t Return Book");
        System.out.println("------------------------");
        //Menu Text
        System.out.print("\nEnter Transaction ID: ");
        int transactionId = getIntInput();
        Transaction transaction = library.findTransactionById(transactionId);

        if (transaction == null) {
            System.out.println("Invalid transaction ID.\n");
            handleUserInput();
        }

        Book book = transaction.getBook();
        if (book.isAvailable()) {
            System.out.println("Book is currently available");
            return;
        }
        //Exit Text
        clearScreen();
        System.out.println("-----------------------------------------------");
        System.out.println("\t  The book was returned successfully!");
        System.out.println("-----------------------------------------------");
        library.returnBook(transactionId);
        System.out.println("-----------------------------------------------");
        handleUserInput();
    }
}
