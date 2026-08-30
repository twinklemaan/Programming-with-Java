import java.util.Scanner;

// Custom checked exception — extending Exception (not RuntimeException) 
// is what makes 'throws' mandatory at compile time, not optional
class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}

public class Login {

    // 'throws' declares that this method does NOT handle the exception itself —
    // it passes responsibility up to whoever calls it (main, below)
    static void checkPassword(String password) throws InvalidPasswordException {
        String correctPassword = "Java@123";
        if (!password.equals(correctPassword)) {
            // 'throw' actually raises the exception object at this exact line
            throw new InvalidPasswordException("Invalid password entered.");
        }
        System.out.println("Login successful.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try {
            checkPassword(password);
        } catch (InvalidPasswordException e) {
            System.out.println("Login failed: " + e.getMessage());
        } finally {
            // Executes whether login succeeded or failed
            System.out.println("Login attempt process completed.");
        }

        sc.close();
    }
}