import java.util.ArrayList;
import java.util.Scanner;

public class ToDoListDemo {
    public static void main(String[] args) {
        ArrayList<String> taskList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----- TO-DO LIST MENU -----");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View Tasks");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // consume leftover newline
                    System.out.print("Enter task to add: ");
                    String newTask = sc.nextLine();
                    taskList.add(newTask);
                    System.out.println("Task added successfully.");
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Enter exact task name to remove: ");
                    String removeTask = sc.nextLine();
                    if (taskList.remove(removeTask)) {
                        System.out.println("Task removed successfully.");
                    } else {
                        System.out.println("Task not found in the list.");
                    }
                    break;

                case 3:
                    // Using StringBuffer to build and display the task list
                    StringBuffer taskDisplay = new StringBuffer();
                    taskDisplay.append("----- Current To-Do List -----\n");

                    if (taskList.isEmpty()) {
                        taskDisplay.append("No tasks pending.\n");
                    } else {
                        for (int i = 0; i < taskList.size(); i++) {
                           taskDisplay.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
                        }
                    }
                    System.out.print(taskDisplay);
                    break;

                case 4:
                    System.out.println("Exiting To-Do List application.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}