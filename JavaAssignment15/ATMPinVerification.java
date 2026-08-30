import java.util.Scanner;

class InvalidPINException extends Exception {
    public InvalidPINException(String message) {
        super(message);
    }
}

public class ATMPinVerification {

    static void verifyPIN(int enteredPIN) throws InvalidPINException {
        final int correctPIN = 4321;
        if (enteredPIN != correctPIN) {
            throw new InvalidPINException("Incorrect PIN entered.");
        }
        System.out.println("PIN verified successfully.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ATM PIN: ");
        int pin = sc.nextInt();

        try {
            verifyPIN(pin);
        } catch (InvalidPINException e) {
            System.out.println("Verification failed: " + e.getMessage());
        } finally {
            System.out.println("PIN verification process completed.");
        }

        sc.close();
    }
}