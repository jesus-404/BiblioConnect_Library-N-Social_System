package org.example;

import org.example.librarySystem.LibraryUI;
import org.example.socialNetworkSystem.NetworkUI;

import java.util.Scanner;

public class MainUI {
    private final Scanner scanner;
    private LibraryUI libraryUI;
    private NetworkUI networkUI;

    public MainUI() {
        this.scanner = new Scanner(System.in);
        this.libraryUI = new LibraryUI();
        this.networkUI = new NetworkUI();
    }

    public void displayMenu() {
        //Title Text
        System.out.println(" ______     __     ______     __         __     ______    ");
        System.out.println("/\\  == \\   /\\ \\   /\\  == \\   /\\ \\       /\\ \\   /\\  __ \\   ");
        System.out.println("\\ \\  __<   \\ \\ \\  \\ \\  __<   \\ \\ \\____  \\ \\ \\  \\ \\ \\/\\ \\  ");
        System.out.println(" \\ \\_____\\  \\ \\_\\  \\ \\_____\\  \\ \\_____\\  \\ \\_\\  \\ \\_____\\ ");
        System.out.println("  \\/_____/   \\/_/   \\/_____/   \\/_____/   \\/_/   \\/_____/ ");
        System.out.println(" ______     ______     __   __     __   __     ______     ______     ______  ");
        System.out.println("/\\  ___\\   /\\  __ \\   /\\ \"-.\\ \\   /\\ \"-.\\ \\   /\\  ___\\   /\\  ___\\   /\\__  _\\ ");
        System.out.println("\\ \\ \\____  \\ \\ \\/\\ \\  \\ \\ \\-.  \\  \\ \\ \\-.  \\  \\ \\  __\\   \\ \\ \\____  \\/_/\\ \\/ ");
        System.out.println(" \\ \\_____\\  \\ \\_____\\  \\ \\_\\\\\"\\_\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\  \\ \\_____\\    \\ \\_\\ ");
        System.out.println("  \\/_____/   \\/_____/   \\/_/ \\/_/   \\/_/ \\/_/   \\/_____/   \\/_____/     \\/_/ ");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t  BiblioConnect System: v1.0\n");
        //Menu Text
        System.out.println("Select an Option:");
        //System.out.println("0. Debug");
        System.out.println(" 1. Library Management System");
        System.out.println(" 2. Literary Social Network");
        System.out.println(" 3. Exit");

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
                    libraryUI.handleUserInput();
                    displayMenu();
                    break;
                case 2:
                    clearScreen();
                    networkUI.handleUserInput();
                    displayMenu();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
        System.exit(0);
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
}
