class BankAccount {
    private final String accountNumber; // final variable
    private String holderName;
    private double balance;

    BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
    }

    // final method: cannot be overridden by any subclass of BankAccount
    final void showDetails() {
        System.out.println("Account No : " + accountNumber);
        System.out.println("Holder     : " + holderName);
        System.out.println("Balance    : Rs. " + balance);
    }
}

public class BankAccountProgram{
    public static void main(String[] args) {
        BankAccount acc = new BankAccount("ACC10234", "Twinkle", 25000);
        acc.deposit(5000);
        acc.showDetails();
    }
}