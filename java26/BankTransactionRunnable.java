/*
 * ASSIGNMENT No: 26 - Exercise 2
 * TITLE: Write a Java program to create and execute multiple threads using the Runnable interface.
 * EXERCISE 2: Create a Java program using the Runnable interface where two tasks run simultaneously:
 *             one thread performs deposit operations, and another performs withdrawal operations,
 *             both displaying results concurrently.
 */

class BankAccount {
    private final String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount, String taskName) {
        System.out.println(">> [" + taskName + "] Deposit request for: $" + amount);
        balance += amount;
        System.out.printf("   [%s] SUCCESS: New Balance is $%.2f%n", taskName, balance);
    }

    public synchronized void withdraw(double amount, String taskName) {
        System.out.println("<< [" + taskName + "] Withdrawal request for: $" + amount);
        if (balance >= amount) {
            balance -= amount;
            System.out.printf("   [%s] SUCCESS: Remaining Balance is $%.2f%n", taskName, balance);
        } else {
            System.out.printf("   [%s] FAILED: Insufficient Balance ($%.2f) for withdrawal of $%.2f%n", taskName, balance, amount);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}

// Deposit task implementing Runnable
class DepositTask implements Runnable {
    private final BankAccount account;
    private final double[] amounts;

    public DepositTask(BankAccount account, double[] amounts) {
        this.account = account;
        this.amounts = amounts;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (double amount : amounts) {
                account.deposit(amount, threadName);
                Thread.sleep(220); // Delay between consecutive deposits
            }
        } catch (InterruptedException e) {
            System.err.println("[" + threadName + "] Interrupted: " + e.getMessage());
        }
    }
}

// Withdrawal task implementing Runnable
class WithdrawTask implements Runnable {
    private final BankAccount account;
    private final double[] amounts;

    public WithdrawTask(BankAccount account, double[] amounts) {
        this.account = account;
        this.amounts = amounts;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (double amount : amounts) {
                account.withdraw(amount, threadName);
                Thread.sleep(260); // Delay between consecutive withdrawals
            }
        } catch (InterruptedException e) {
            System.err.println("[" + threadName + "] Interrupted: " + e.getMessage());
        }
    }
}

public class BankTransactionRunnable {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println(" Assignment 26 - Exercise 2: Bank Transactions via Runnable");
        System.out.println("=========================================================\n");

        BankAccount account = new BankAccount("Twinkle - Savings A/C", 1200.00);
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.printf("Initial Balance: $%.2f%n%n", account.getBalance());

        double[] deposits = { 300.0, 500.0, 200.0, 450.0 };
        double[] withdrawals = { 250.0, 800.0, 1000.0, 150.0 };

        // 1. Create Runnable tasks
        Runnable depositTask = new DepositTask(account, deposits);
        Runnable withdrawTask = new WithdrawTask(account, withdrawals);

        // 2. Wrap them into Thread objects
        Thread thread1 = new Thread(depositTask, "Runnable-Deposit");
        Thread thread2 = new Thread(withdrawTask, "Runnable-Withdraw");

        // 3. Execute concurrently
        System.out.println("Starting concurrent execution of deposit and withdrawal tasks...\n");
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Transaction process interrupted: " + e.getMessage());
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.printf("All tasks completed. Final Balance: $%.2f%n", account.getBalance());
        System.out.println("---------------------------------------------------------");
    }
}
