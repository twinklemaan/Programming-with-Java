
class BankAccount {
    private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount, String threadName) {
        System.out.println("[" + threadName + "] Initiating DEPOSIT of $" + amount);
        balance += amount;
        System.out.println("[" + threadName + "] Deposit successful! Current Balance: $" + balance);
    }

    public synchronized void withdraw(double amount, String threadName) {
        System.out.println("[" + threadName + "] Initiating WITHDRAWAL of $" + amount);
        if (balance >= amount) {
            balance -= amount;
            System.out.println("[" + threadName + "] Withdrawal successful! Remaining Balance: $" + balance);
        } else {
            System.out.println("[" + threadName + "] Insufficient funds! Failed to withdraw $" + amount + ". Current Balance: $" + balance);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

class DepositThread extends Thread {
    private final BankAccount account;
    private final double[] depositAmounts;

    public DepositThread(BankAccount account, double[] depositAmounts) {
        super("Deposit-Thread");
        this.account = account;
        this.depositAmounts = depositAmounts;
    }

    @Override
    public void run() {
        try {
            for (double amount : depositAmounts) {
                account.deposit(amount, getName());
                // Simulate delay between deposit transactions
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            System.err.println(getName() + " interrupted: " + e.getMessage());
        }
    }
}

class WithdrawThread extends Thread {
    private final BankAccount account;
    private final double[] withdrawAmounts;

    public WithdrawThread(BankAccount account, double[] withdrawAmounts) {
        super("Withdraw-Thread");
        this.account = account;
        this.withdrawAmounts = withdrawAmounts;
    }

    @Override
    public void run() {
        try {
            for (double amount : withdrawAmounts) {
                account.withdraw(amount, getName());
                // Simulate delay between withdrawal transactions
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            System.err.println(getName() + " interrupted: " + e.getMessage());
        }
    }
}

public class BankTransactionSimulation {
    public static void main(String[] args) {
      

        BankAccount sharedAccount = new BankAccount("SB-100234", 1000.00);
        System.out.println("Account Number: " + sharedAccount.getAccountNumber());
        System.out.println("Initial Balance: $" + sharedAccount.getBalance() + "\n");

        double[] deposits = { 200.0, 500.0, 300.0, 450.0 };
        double[] withdrawals = { 150.0, 400.0, 800.0, 200.0 };

        DepositThread depositThread = new DepositThread(sharedAccount, deposits);
        WithdrawThread withdrawThread = new WithdrawThread(sharedAccount, withdrawals);

        System.out.println("Starting concurrent deposit and withdrawal threads...\n");
        depositThread.start();
        withdrawThread.start();

        try {
            depositThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            System.err.println("Transaction process interrupted: " + e.getMessage());
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.println("All transactions finished.");
        System.out.println("Final Account Balance: $" + sharedAccount.getBalance());
        System.out.println("---------------------------------------------------------");
    }
}
