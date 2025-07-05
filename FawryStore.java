import java.util.Scanner;

public class FawryStore {
    private static Scanner scanner = new Scanner(System.in);
    private static Profile profile = new Profile();
    private static Products products = new Products();
    
    public static void main(String[] args) {
        System.out.println("=== FawryStore ===");
        
        boolean running = true;
        
        while (running) {
            fawrystoreMenu();
             int choice = -1;

            while (true) {
                choice = getChoice();
                if (choice >= 1 && choice <= 4) {
                    break;
                } else {
                    System.out.println("invalid please select 1-4");
                    System.out.print("Enter your choice (1-4): "); 
                }
            }
            
            switch (choice) {
                case 1:
                    utils.clearScreen();
                    profile.ProfileMenu();
                    break;
                case 2: 
                    utils.clearScreen();
                    products.ProductsMenu();
                    break;
                case 3:
                    System.out.println("thank you for visiting FawryStore");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }
    
    private static void fawrystoreMenu() {
        System.out.println("1.Profile Management");
        System.out.println("2.Products");
        System.out.println("3.Exit");
        System.out.print("Enter your choice (1-3): ");
    }   
    
    private static int getChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }

    
    public static Scanner getScanner() {
        return scanner;
    }
}