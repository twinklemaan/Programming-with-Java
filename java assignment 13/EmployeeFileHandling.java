import java.io.*;

public class EmployeeFileHandling {
    public static void main(String[] args) {

        String fileName = "employee.txt";

        // Create a File object
        File file = new File(fileName);

        // Writing employee details to the file
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("Employee ID : 101");
            writer.newLine();
            writer.write("Name        : Ananya Rao");
            writer.newLine();
            writer.write("Department  : IT");
            writer.newLine();
            writer.write("Salary      : Rs. 55000");
            writer.newLine();

            writer.close();

            System.out.println("Employee details written successfully.");

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }

        // Reading employee details from the file
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;

            System.out.println("\nEmployee Details:");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}