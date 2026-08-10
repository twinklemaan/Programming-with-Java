import facultypkg.Faculty;
import studentpkg.Student;

public class CollegeProgram {
    public static void main(String[] args) {
        Student s = new Student("Twinkle Sharma", 21, "Computer Science Engineering");
        s.showDetails();

        System.out.println();

        Faculty f = new Faculty("Dr. Ananya Rao", 302, "Data Structures");
        f.showDetails();
    }
}