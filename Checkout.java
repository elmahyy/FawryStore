import java.util.Scanner;

public class Checkout {
    private Scanner scanner = FawryStore.getScanner();
    
    public void CheckoutMenu() {
        System.out.println("=== CHECKOUT COMPLETE ===");
        System.out.println("Thank you for your purchase!");
        System.out.println("Your order has been processed successfully");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        utils.clearScreen();
    }
}