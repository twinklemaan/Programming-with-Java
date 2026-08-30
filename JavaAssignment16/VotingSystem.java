import java.util.Scanner;

// Custom exception for underage voting attempts
class UnderageException extends Exception {
    public UnderageException(String message) {
        super(message);
    }
}

public class VotingSystem {

    static void checkVotingEligibility(int age) throws UnderageException {
        if (age < 18) {
            throw new UnderageException("You must be at least 18 years old to vote. Entered age: " + age);
        }
        System.out.println("You are eligible to vote.");
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter your age: ");
            int age = sc.nextInt();
            try {
                checkVotingEligibility(age);
            } catch (UnderageException e) {
                System.out.println("Voting registration failed: " + e.getMessage());
            } finally {
                System.out.println("Voting eligibility check completed.");
            }
        }
    }
}