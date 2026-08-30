import java.util.Scanner;

public class OnlineShopping {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter product quantity: ");
            int quantity = sc.nextInt();
            double pricePerUnit = 250.0;

            try {
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }

                double total = quantity * pricePerUnit;
                System.out.println("Order placed successfully.");
                System.out.println("Total amount: Rs. " + total);

            } catch (IllegalArgumentException e) {
                System.out.println("Order failed: " + e.getMessage());
            }
        }
    }
}


