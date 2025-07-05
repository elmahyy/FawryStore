import java.util.Scanner;
import java.io.*;

public class Profile {
    private Scanner scanner = FawryStore.getScanner();
    private static double balance = utils.Default_balance;
    private static final String DATA = "data.txt";

    public Profile() {
        loadBalance();
    }

    public void ProfileMenu() {
        int choice = -1;
        while (true) {
        System.out.println("Profile");
        System.out.println("User: Ahmed Elmahy");
        System.out.println("Balance: £" + utils.getBalance());
        System.out.println("Address: New Cairo, Egypt");
        System.out.println("-----------");
        System.out.println("1. Change Balance");
        System.out.println("2. Back to Main Menu");
        System.out.print("Enter your choice (1-3): ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        changeBalance();
                        break;
                    case 2:
                        utils.clearScreen();
                        return;
                    default:
                        System.out.println("Invalid choice. Please select 1-3.");
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("invalid please select 1-4");
            }
        }
    }
    

    private void changeBalance() {
        System.out.print("Enter amount: £");
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (amount > 0) {
                balance = amount;
                saveBalance(); 
                System.out.println("Balance updated! New balance: £" + balance);
                utils.delayScreen();
                utils.clearScreen();
            } else {
                System.out.println("Please enter a positive amount");
                utils.delayScreen();
                utils.clearScreen();
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("invalid amount");
        }
    }


    private void saveBalance() {
        try {
            PrintWriter writer = new PrintWriter(DATA);
            writer.println("balance=" + balance);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error Saving Balance");
        }
    }

    private void loadBalance() {
       balance = utils.getBalance();
       if (balance == utils.Default_balance) {
        saveBalance();
       }
    }

}