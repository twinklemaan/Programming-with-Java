/*
 * ASSIGNMENT No: 25 - Exercise 1
 * TITLE: Write a Java program using the Thread class to create multiple threads and demonstrate their lifecycle.
 * EXERCISE 1: Create a program where multiple threads print numbers simultaneously.
 */

class NumberPrinterThread extends Thread {
    private final int start;
    private final int end;
    private final int delayMs;

    public NumberPrinterThread(String threadName, int start, int end, int delayMs) {
        super(threadName);
        this.start = start;
        this.end = end;
        this.delayMs = delayMs;
    }

    @Override
    public void run() {
        System.out.println("[State: RUNNABLE] " + getName() + " started printing numbers.");
        try {
            for (int i = start; i <= end; i++) {
                System.out.println(getName() + " -> Printed Number: " + i);
                // Demonstrates TIMED_WAITING lifecycle state and allows thread interleaving
                Thread.sleep(delayMs);
            }
        } catch (InterruptedException e) {
            System.err.println(getName() + " was interrupted: " + e.getMessage());
        }
        System.out.println(getName() + " finished execution.");
    }
}

public class NumberPrinter {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println(" Assignment 25 - Exercise 1: Multiple Threads Printing Numbers");
        System.out.println("=========================================================\n");

        // 1. Create multiple threads (Lifecycle state: NEW)
        NumberPrinterThread thread1 = new NumberPrinterThread("Thread-Alpha", 1, 5, 200);
        NumberPrinterThread thread2 = new NumberPrinterThread("Thread-Beta", 10, 15, 150);
        NumberPrinterThread thread3 = new NumberPrinterThread("Thread-Gamma", 100, 105, 180);

        System.out.println("Demonstrating Thread Lifecycle States before start():");
        System.out.println(thread1.getName() + " State: " + thread1.getState()); // NEW
        System.out.println(thread2.getName() + " State: " + thread2.getState()); // NEW
        System.out.println(thread3.getName() + " State: " + thread3.getState()); // NEW
        System.out.println();

        // 2. Start threads simultaneously (Lifecycle state: RUNNABLE)
        System.out.println("Starting threads simultaneously...");
        thread1.start();
        thread2.start();
        thread3.start();

        // Check state shortly after start
        try {
            Thread.sleep(50);
            System.out.println("\n[Lifecycle Inspection while executing]");
            System.out.println(thread1.getName() + " State: " + thread1.getState());
            System.out.println(thread2.getName() + " State: " + thread2.getState());
            System.out.println(thread3.getName() + " State: " + thread3.getState());
            System.out.println("--------------------------------------------------\n");

            // 3. Wait for all threads to terminate using join()
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

        // 4. Lifecycle state after completion: TERMINATED
        System.out.println("\nDemonstrating Thread Lifecycle States after completion:");
        System.out.println(thread1.getName() + " State: " + thread1.getState()); // TERMINATED
        System.out.println(thread2.getName() + " State: " + thread2.getState()); // TERMINATED
        System.out.println(thread3.getName() + " State: " + thread3.getState()); // TERMINATED
        System.out.println("\nAll threads completed successfully.");
    }
}
