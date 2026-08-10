interface Printable {
    void print(); // implicitly public and abstract
}

class Student implements Printable {
    private String name;
    private int rollNo;
    private String course;

    Student(String name, int rollNo, String course) {
        this.name = name;
        this.rollNo = rollNo;
        this.course = course;
    }

    @Override
    public void print() {
        System.out.println("---- Student Details ----");
        System.out.println("Name    : " + name);
        System.out.println("Roll No : " + rollNo);
        System.out.println("Course  : " + course);
    }
}

class Employee implements Printable {
    private String name;
    private int empId;
    private String department;

    Employee(String name, int empId, String department) {
        this.name = name;
        this.empId = empId;
        this.department = department;
    }

    @Override
    public void print() {
        System.out.println("---- Employee Details ----");
        System.out.println("Name       : " + name);
        System.out.println("Emp ID     : " + empId);
        System.out.println("Department : " + department);
    }
}

public class PrintableProgram {
    public static void main(String[] args) {
        // Printable p = new Printable(); // ERROR: cannot instantiate an interface

        Printable s = new Student("Twinkle", 205, "Computer Science Engineering");
        s.print();

        System.out.println();

        Printable e = new Employee("Rohan Mehta", 5023, "Human Resources");
        e.print();
    }
}
