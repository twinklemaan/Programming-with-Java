/*
 * ASSIGNMENT No: 26 - Exercise 1
 * TITLE: Write a Java program to create and execute multiple threads using the Runnable interface.
 * EXERCISE 1: Create a program using Runnable to run two tasks simultaneously.
 */

// First task implementing Runnable
class TaskOne implements Runnable {
    private final String taskName;
    private final int count;

    public TaskOne(String taskName, int count) {
        this.taskName = taskName;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("[" + taskName + "] Started (Task: Computing & Printing Squares)...");
        try {
            for (int i = 1; i <= count; i++) {
                int square = i * i;
                System.out.println("  -> [" + taskName + "] Square of " + i + " = " + square);
                Thread.sleep(150); // Simulates task computation delay
            }
        } catch (InterruptedException e) {
            System.err.println("[" + taskName + "] Interrupted: " + e.getMessage());
        }
        System.out.println("[" + taskName + "] Completed.");
    }
}

// Second task implementing Runnable
class TaskTwo implements Runnable {
    private final String taskName;
    private final int count;

    public TaskTwo(String taskName, int count) {
        this.taskName = taskName;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("[" + taskName + "] Started (Task: Character Sequence Generator)...");
        try {
            char ch = 'A';
            for (int i = 1; i <= count; i++) {
                System.out.println("  => [" + taskName + "] Generated Character: " + ch + " (Step " + i + ")");
                ch++;
                Thread.sleep(180); // Simulates task computation delay
            }
        } catch (InterruptedException e) {
            System.err.println("[" + taskName + "] Interrupted: " + e.getMessage());
        }
        System.out.println("[" + taskName + "] Completed.");
    }
}

public class SimultaneousTasksRunnable {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println(" Assignment 26 - Exercise 1: Run Two Tasks using Runnable");
        System.out.println("=========================================================\n");

        // 1. Instantiate the Runnable tasks
        Runnable task1 = new TaskOne("Task-SquareCalculator", 6);
        Runnable task2 = new TaskTwo("Task-CharacterGenerator", 6);

        // 2. Pass the Runnable objects to Thread constructors
        Thread thread1 = new Thread(task1, "Worker-Thread-1");
        Thread thread2 = new Thread(task2, "Worker-Thread-2");

        // 3. Start both threads to run tasks simultaneously
        System.out.println("Launching both tasks simultaneously...\n");
        thread1.start();
        thread2.start();

        // 4. Wait for both threads to complete execution
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.println("Both Runnable tasks have finished execution successfully.");
        System.out.println("---------------------------------------------------------");
    }
}
