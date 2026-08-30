import java.util.Scanner;

public class ATMWithdrawal {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            double balance = 5000.0;   // Assume current account balance
            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();
            try {
                if (amount <= 0) {
                    // Manually raised exception — JVM has no built-in for this business rule
                    throw new IllegalArgumentException("Withdrawal amount must be positive.");
                }
                if (amount > balance) {
                    throw new IllegalArgumentException("Insufficient balance. Available: Rs." + balance);
                }
                
                balance -= amount;
                System.out.println("Withdrawal successful. New balance: Rs." + balance);
                
            } catch (IllegalArgumentException e) {
                System.out.println("Transaction failed: " + e.getMessage());
            } finally {
                // finally runs regardless of exception, useful for logging/closing resources
                System.out.println("ATM session ended.");
            }

        } // Assume current account balance
    }
}