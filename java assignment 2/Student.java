class Student {
    String name;
    int rollNumber;

    // Default constructor
    Student() {
        name = "Unknown";
        rollNumber = 0;
    }

    // Parameterized constructor
    Student(String name, int rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
    }

    void display() {
        System.out.println("Name: " + name + ", Roll Number: " + rollNumber);
    }
}

 class StudentDemo {
    public static void main(String[] args) {
        // Using default constructor
        Student s1 = new Student();
        System.out.println("Student 1 (Default Constructor):");
        s1.display();

        // Using parameterized constructor
        Student s2 = new Student("Aarav Sharma", 21);
        System.out.println("\nStudent 2 (Parameterized Constructor):");
        s2.display();
    }
}