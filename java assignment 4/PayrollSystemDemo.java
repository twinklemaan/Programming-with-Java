import java.util.Scanner;

class Employee {
    Integer employeeId;
    Double basicSalary;
    Double bonus;

    Employee(Integer employeeId, Double basicSalary, Double bonus) {
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    // Validate that salary is a positive, non-null value
    boolean isValidSalary() {
        return basicSalary != null && basicSalary > 0;
    }

    // Calculate net salary (basic + bonus)
    double calculateNetSalary() {
        return basicSalary + bonus;
    }

    void displayDetails() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Basic Salary: Rs." + basicSalary);
        System.out.println("Bonus: Rs." + bonus);
    }
}

public class PayrollSystemDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Accepting input from the user (entered as text/String by default)
        System.out.print("Enter Employee ID: ");
        String idInput = sc.next();

        System.out.print("Enter Basic Salary: ");
        String salaryInput = sc.next();

        System.out.print("Enter Bonus Amount: ");
        String bonusInput = sc.next();

        // Converting the user-entered String values into wrapper objects
        Integer empId = Integer.valueOf(idInput);
        Double salary = Double.valueOf(salaryInput);
        Double bonusAmount = Double.valueOf(bonusInput);

        Employee emp = new Employee(empId, salary, bonusAmount);

        System.out.println("\n---- Employee Payroll Details ----");
        emp.displayDetails();

        // Validate salary before calculating net pay
        if (emp.isValidSalary()) {
            double netSalary = emp.calculateNetSalary();
            System.out.println("Net Salary: Rs." + netSalary);
        } else {
            System.out.println("Invalid salary entered. Cannot process payroll.");
        }

        sc.close();
    }
}