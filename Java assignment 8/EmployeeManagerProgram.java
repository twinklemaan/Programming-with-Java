// Exercise 1 - Employee and Manager Program
// Manager accesses Employee details using the super keyword.

class Employee {
    protected String name;
    protected int empId;
    protected double baseSalary;

    Employee(String name, int empId, double baseSalary) {
        this.name = name;
        this.empId = empId;
        this.baseSalary = baseSalary;
    }

    void showDetails() {
        System.out.println("Emp ID      : " + empId);
        System.out.println("Name        : " + name);
        System.out.println("Base Salary : Rs. " + baseSalary);
    }
}

class Manager extends Employee {
    private double teamAllowance;

    Manager(String name, int empId, double baseSalary, double teamAllowance) {
        // super(...) calls the Employee constructor to initialize inherited fields
        super(name, empId, baseSalary);
        this.teamAllowance = teamAllowance;
    }

    @Override
    void showDetails() {
        // super.showDetails() reuses the parent's method instead of duplicating it
        super.showDetails();
        System.out.println("Allowance   : Rs. " + teamAllowance);

        // super.name / super.baseSalary - directly accessing parent's fields
        // (works here since they are declared protected, not private)
        double totalSalary = super.baseSalary + teamAllowance;
        System.out.println("Total Salary: Rs. " + totalSalary);
    }
}

public class EmployeeManagerProgram {
    public static void main(String[] args) {
        Manager mgr = new Manager("Ananya Rao", 101, 55000, 12000);
        mgr.showDetails();
    }
}