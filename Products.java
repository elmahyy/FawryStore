import java.util.Scanner;
import java.io.*;

public class Products {
    private Scanner scanner = FawryStore.getScanner();
    private String[] cart = new String[15]; //max items in cart
    private int cartCount = 0;
    
    public void ProductsMenu() { 
        int choice = -1;
        while (true) {
            System.out.println("=== PRODUCTS MENU ===");
            System.out.println("Balance: £" + getBalance());
            System.out.println("Cart Items: " + cartCount);
            utils.showProducts();
            System.out.println("0. Go to Checkout");
            System.out.println("99. Back to Main Menu");
            System.out.print("Enter product number (0 for checkout, 99 for main menu): ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                
                if (choice == 0) {
                    if (cartCount > 0) {
                        goToCheckout();
                    } else {
                        System.out.println("cart is empty");
                        utils.delayScreen();
                    }
                } else if (choice == 99) {
                    utils.clearScreen();
                    return;
                } else {
                    addToCart(choice);
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("invalid please select 1-4");
                utils.delayScreen();
                utils.clearScreen();
            }
        }
    }
    

   
    
    private void addToCart(int productNum) {
        try {
            Scanner file = new Scanner(new File("products.txt"));
            int count = 1;
            String selectedProduct = "";
            
            // finding prodict
            while (file.hasNextLine()) {
                String line = file.nextLine();
                if (line.startsWith("name=")) {
                    if (count == productNum) {
                        selectedProduct = line;
                        break;
                    }
                    count++;
                }
            }
            file.close();
            
            if (selectedProduct.equals("")) {
                System.out.println("invalid number");
                utils.delayScreen();
                return;
            }
            
            String[] parts = selectedProduct.split(",");
            String name = parts[0].split("=")[1];
            int availableQuantity = Integer.parseInt(parts[2].split("=")[1]); //getinstock
            String expiry = parts[3].split("=")[1]; //getexpired

            if (!expiry.equals("N/A") && isExpired(expiry)) { //isexpired?
            System.out.println(name + " is expired (Expiry: " + expiry + ").");
            utils.delayScreen();
            utils.clearScreen();
            return;
        }
            
            if (availableQuantity <= 0) { //instock?
                System.out.println(name + " is out of stock.");
                utils.delayScreen();
                utils.clearScreen(); //test
                return;
            }
            
            // have in cart
            int cartQuantity = countItemInCart(name);
            
            if (cartQuantity >= availableQuantity) {
                System.out.println("cannot add more " + name + ". Only " + availableQuantity + " available.");
                utils.delayScreen();
                return;
            }
            
            cart[cartCount] = selectedProduct;
            cartCount++;
            System.out.println(name + " added to cart!");
            utils.delayScreen();
            utils.clearScreen();
            
        } catch (Exception e) {
            System.out.println("Cart is full!"); //test
            utils.delayScreen();
        }
    }

    private boolean isExpired(String expiryDate) {
        try{
            String[] dateParts = expiryDate.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]);
            int day = Integer.parseInt(dateParts[2]);

            //EXPIRATION DATE
            int currentYear = 2025;
            int currentMonth = 1;
            int currentDay = 1;

            if (year < currentYear) return true;
            if (year == currentYear && month < currentMonth) return true;
            if (year == currentYear && month == currentMonth && day <currentDay) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    private int countItemInCart(String productName) {
        int count = 0;
        for (int i = 0; i < cartCount; i++) {
            if (cart[i] != null) {
                String[] parts = cart[i].split(",");
                String name = parts[0].split("=")[1];
                if (name.equals(productName)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private double calculateDeliveryFee() {
        double totalWeight = 0;
        
        // calculate total weight
        for (int i = 0; i < cartCount; i++) {
            if (cart[i] != null) {
                String[] parts = cart[i].split(",");
                double weight = Double.parseDouble(parts[4].split("=")[1]);
                totalWeight += weight;
            }
        }


                                         //////     PRICESS PER WEIGHT      ////////
        
        // free delivery
        if (totalWeight == 0) {
            return 0.0;
        }
        if (totalWeight <= 0.5) {
            return 5.0;
        } else if (totalWeight <= 1.0) {
            return 10.0;
        } else if (totalWeight <= 5.0) {
            return 20.0;
        } else if (totalWeight <= 10.0) {
            return 35.0;
        } else if (totalWeight <= 25.0) {
            return 50.0;
        } else {
            return 75.0;
        }
    }
    
    
    
    private void goToCheckout() {
        System.out.println("** YOUR CART **");
        double subtotal = 0;
        double deliveryFee = calculateDeliveryFee();
        
        // show the cart
        for (int i = 0; i < cartCount; i++) {
            String[] parts = cart[i].split(",");
            String name = parts[0].split("=")[1];
            double price = Double.parseDouble(parts[1].split("=")[1]);
            double weight = Double.parseDouble(parts[4].split("=")[1]);
            
            String weightDisplay = weight == 0 ? "Digital" : weight + "kg";
            System.out.println((i + 1) + ". " + name + " - £" + price + " (" + weightDisplay + ")");
            subtotal += price;
        }
        
        System.out.println("=== PRICING BREAKDOWN ===");
        System.out.println("Subtotal: £" + subtotal);
        
        if (deliveryFee > 0) {
            System.out.println("Delivery Fee: £" + deliveryFee);
        } else {
            System.out.println("Delivery Fee: FREE (Digital products only)");
        }
        
        double total = subtotal + deliveryFee;
        System.out.println("Total: £" + total);
        System.out.println("Your Balance: £" + getBalance());
        
        // does user have enough balance?
        if (total > getBalance()) {
            System.out.println("Insufficient balance for checkout");
            System.out.print("Remove items from cart? (y/n): ");
            String remove = scanner.nextLine();
            if (remove.equalsIgnoreCase("y")) {
                clearCart();
            }
            utils.delayScreen();
            return;
        }
        
        System.out.print("Proceed to checkout? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            processCheckout(total);
        } else {
            System.out.println("Checkout cancelled");
            utils.delayScreen();
        }
    }

    
    private void processCheckout(double total) {
        try {
           
            
            updateBalance(total);
            updateProductQuantities();
            
            System.out.println("Checkout successful!");
            System.out.println("Total paid: £" + total);
            System.out.println("New balance: £" + (getBalance()));
            
            double deliveryFee = calculateDeliveryFee();
            if (deliveryFee > 0) {
                System.out.println("Delivery fee: £" + deliveryFee);
                System.out.println("Your order will be delivered within 3-5 business days");
            } else {
                System.out.println("Check your email for digital products.");
            }
            
            clearCart();
            
            Checkout checkout = new Checkout();
            checkout.CheckoutMenu();
            
        } catch (Exception e) {
            System.out.println("error :[");
            e.printStackTrace();
        }
        utils.delayScreen();
        utils.clearScreen();
    }
    
    private void updateBalance(double total) throws Exception {
        Scanner file = new Scanner(new File("data.txt"));
        String[] lines = new String[100];
        int lineCount = 0;
        
        while (file.hasNextLine()) {
            lines[lineCount] = file.nextLine();
            lineCount++;
        }
        file.close();
        
        double newBalance = getBalance() - total;
        for (int i = 0; i < lineCount; i++) {
            if (lines[i].startsWith("balance=")) {
                lines[i] = "balance=" + newBalance;
                break;
            }
        }
        
        PrintWriter writer = new PrintWriter("data.txt");
        for (int i = 0; i < lineCount; i++) {
            writer.println(lines[i]);
        }
        writer.close();
    }
    
    private void updateProductQuantities() throws Exception {
        Scanner file = new Scanner(new File("products.txt"));
        String[] lines = new String[100];
        int lineCount = 0;
        
        while (file.hasNextLine()) {
            lines[lineCount] = file.nextLine();
            lineCount++;
        }
        file.close();
        
        for (int i = 0; i < cartCount; i++) {
            String[] cartParts = cart[i].split(",");
            String cartName = cartParts[0].split("=")[1];
            
            for (int j = 0; j < lineCount; j++) {
                if (lines[j].startsWith("name=")) {
                    String[] lineParts = lines[j].split(",");
                    String lineName = lineParts[0].split("=")[1];
                    
                    if (cartName.equals(lineName)) {
                        int quantity = Integer.parseInt(lineParts[2].split("=")[1]);
                        quantity--;
                        lines[j] = lineParts[0] + "," + lineParts[1] + ",quantity=" + quantity + "," + lineParts[3] + "," + lineParts[4];
                        break;
                    }
                }
            }
        }
        
        PrintWriter writer = new PrintWriter("products.txt");
        for (int i = 0; i < lineCount; i++) {
            writer.println(lines[i]);
        }
        writer.close();
    }
    
    private void clearCart() {
        cartCount = 0;
        for (int i = 0; i < cart.length; i++) {
            cart[i] = null;
        }
    }
    
    private double getBalance() {
        return utils.getBalance();
    }
}