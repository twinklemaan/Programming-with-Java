import java.util.Scanner;

class UnderageException extends Exception {
    public UnderageException(String message) {
        super(message);
    }
}

public class DrivingLicenseSystem {

    static void checkLicenseEligibility(int age) throws UnderageException {
        if (age < 18) {
            throw new UnderageException("You must be at least 18 years old to apply for a driving license. Entered age: " + age);
        }
        System.out.println("You are eligible for a driving license.");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        try {
            checkLicenseEligibility(age);
        } catch (UnderageException e) {
            System.out.println("License application failed: " + e.getMessage());
        } finally {
            System.out.println("License eligibility check completed.");
        }

        sc.close();
    }
}