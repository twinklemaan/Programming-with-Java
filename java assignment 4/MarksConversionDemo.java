class MarksConverter {
    // Method to convert a String mark to an Integer using wrapper class
    int convertToInt(String markStr) {
        return Integer.parseInt(markStr);   // parsing String -> int
    }
}

public class MarksConversionDemo {
    public static void main(String[] args) {
        MarksConverter converter = new MarksConverter();

        // Marks received in String format 
        String mathsMark = "85";
        String scienceMark = "78";
        String englishMark = "90";

        // Convert String marks to Integer using wrapper class methods
        int maths = converter.convertToInt(mathsMark);
        int science = converter.convertToInt(scienceMark);
        int english = converter.convertToInt(englishMark);

        // Alternative: using Integer wrapper object directly 
        Integer historyMark = Integer.valueOf("82");

        System.out.println("Maths Marks (converted): " + maths);
        System.out.println("Science Marks (converted): " + science);
        System.out.println("English Marks (converted): " + english);
        System.out.println("History Marks (converted): " + historyMark);

        // Calculate total marks
        int totalMarks = maths + science + english + historyMark;
        System.out.println("\nTotal Marks: " + totalMarks);

        // Calculate average (demonstrating unboxing in arithmetic)
        double averageMarks = totalMarks / 4.0;
        System.out.println("Average Marks: " + averageMarks);
    }
}