import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistrationDemo {
    public static void main(String[] args) {
        ArrayList<String> registeredCourses = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----- STUDENT COURSE REGISTRATION MENU -----");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. View Registered Courses");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine();
                    System.out.print("Enter course name to register: ");
                    String newCourse = sc.nextLine();
                    if (registeredCourses.contains(newCourse)) {
                        System.out.println("Course already registered.");
                    } else {
                        registeredCourses.add(newCourse);
                        System.out.println("Course registered successfully.");
                    }
                    break;

                case 2:
                    sc.nextLine();
                    System.out.print("Enter exact course name to remove: ");
                    String removeCourse = sc.nextLine();
                    if (registeredCourses.remove(removeCourse)) {
                        System.out.println("Course removed successfully.");
                    } else {
                        System.out.println("Course not found in your registrations.");
                    }
                    break;

                case 3:
    // Using StringBuffer to generate and display the registered course list
               StringBuffer courseList = new StringBuffer();
               courseList.append("----- Registered Courses -----\n");

               if (registeredCourses.isEmpty()) {
               courseList.append("No courses registered yet.\n");
               } else {
               for (int i = 0; i < registeredCourses.size(); i++) {
               courseList.append(i + 1).append(". ").append(registeredCourses.get(i)).append("\n");
               }
               }
               courseList.append("Total Courses Registered: ").append(registeredCourses.size());
               System.out.println(courseList);
               break;

                case 4:
                    System.out.println("Exiting Course Registration System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}
