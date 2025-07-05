import java.io.File;
import java.util.Scanner;

public class utils {
    
    public static final double Default_balance = 5000.0;
    
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {}
    }

    public static void delayScreen() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
    }


                //                  VIEWING PRODUCTS                        ///

    public static void showProducts() {
        System.out.println("=== AVAILABLE PRODUCTS ===");
        try {
            Scanner file = new Scanner(new File("products.txt"));
            int count = 1;
            while (file.hasNextLine()) {
                String line = file.nextLine();
                if (line.startsWith("name=")) {
                    String[] parts = line.split(",");
                    String name = parts[0].split("=")[1]; 
                    String price = parts[1].split("=")[1];
                    String quantity = parts[2].split("=")[1];
                    String expiry = parts[3].split("=")[1];
                    double weight = Double.parseDouble(parts[4].split("=")[1]);
                    
                    String weightDisplay;
                    if (weight == 0) {
                        weightDisplay = "Digital";
                    } else {
                        weightDisplay = weight + "kg";
                    }
                    
                    System.out.println(count + ". " + name + " - £" + price + " (Stock: " + quantity + ", Expiry: " + expiry + ", Weight: " + weightDisplay + ")");
                    count++;
                }
            }
            file.close();
        } catch (Exception e) {
            System.out.println("error :[");
        }
    }
    

    public static double getBalance() {
            try (Scanner file = new Scanner(new File("data.txt"))) {
                while (file.hasNextLine()) {
                    String line = file.nextLine();
                    if (line.startsWith("balance=")) {
                        return Double.parseDouble(line.split("=")[1]);
                    }
                }
            } catch (Exception e) {}
            return Default_balance;
        }


    }
