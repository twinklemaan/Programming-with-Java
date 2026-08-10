import java.io.*;

public class PatientFileHandling {
    public static void main(String[] args) {

        String fileName = "patient.txt";

        // Create a File object
        File file = new File(fileName);

        // Writing patient details to the file
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write("Patient ID : P101");
            writer.newLine();
            writer.write("Name       : Rahul Sharma");
            writer.newLine();
            writer.write("Age        : 35");
            writer.newLine();
            writer.write("Diagnosis  : Fever");
            writer.newLine();

            writer.close();

            System.out.println("Patient details written successfully.");

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }

        // Reading patient details from the file
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line;

            System.out.println("\nPatient Details:");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
        }
    }
}
