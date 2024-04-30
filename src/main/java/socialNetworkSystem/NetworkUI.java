package socialNetworkSystem;
import java.util.Scanner;

public class NetworkUI {

    private final Scanner scanner;

    public NetworkUI() {
        this.scanner = new Scanner(System.in);
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
        System.out.println(" 1. Something");
        System.out.println(" 2. Exit");
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

                    break;
                case 2:
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
}
