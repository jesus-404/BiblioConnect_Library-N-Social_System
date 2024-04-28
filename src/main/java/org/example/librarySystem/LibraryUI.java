package org.example.librarySystem;
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
            type = "fiction";
        } else {
            type = "non-fiction";
        }
        //Exit Text
        clearScreen();
        Book book = new Book(isbn, title, author, genre, type, true);
        library.addBook(book);
        handleUserInput();
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
        String password = "";
        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (!password.isEmpty()) {
                break;
            }
        }
        System.out.print("Enter Patron Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Are You a Student or Faculty Patron? (student/faculty/none): ");
        String patronType = scanner.nextLine();
        if (patronType.equalsIgnoreCase("student") ||
            patronType.equalsIgnoreCase("s") ||
            patronType.equals("1")) {

            patronType = "student";
            System.out.print("Enter a Student ID: ");
            id = getIntInput();
        } else if (patronType.equalsIgnoreCase("faculty") ||
                   patronType.equalsIgnoreCase("f") ||
                   patronType.equals("2")) {

            patronType = "faculty";
            System.out.print("Enter a Faculty ID: ");
            id = getIntInput();
        } else {
            patronType = "none";
            id = -1;
        }
        //Exit Text
        clearScreen();
        Patron patron = new Patron(name, password, contactInfo, patronType, id);
        library.addPatron(patron);
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

        //Exit Text
        clearScreen();
        library.borrowBook(patronId, isbn);
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
        //Exit Text
        clearScreen();
        library.returnBook(transactionId);
        handleUserInput();
    }
}
