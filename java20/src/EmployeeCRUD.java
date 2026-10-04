import java.sql.*;
import java.util.Scanner;

public class EmployeeCRUD {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connected to database.");
            int ch;
            do {
                System.out.println("\n1.Insert 2.View 3.Update 4.Delete 5.Exit");
                System.out.print("Choice: ");
                ch = sc.nextInt();
                switch (ch) {
                    case 1: insert(con, sc); break;
                    case 2: view(con); break;
                    case 3: update(con, sc); break;
                    case 4: delete(con, sc); break;
                    case 5: System.out.println("Bye."); break;
                    default: System.out.println("Invalid choice.");
                }
            } while (ch != 5);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    static void insert(Connection con, Scanner sc) throws SQLException {
        System.out.print("ID: ");         int id = sc.nextInt();
        System.out.print("Name: ");       String name = sc.next();
        System.out.print("Department: "); String dept = sc.next();
        System.out.print("Salary: ");     double sal = sc.nextDouble();

        String sql = "INSERT INTO EmployeeRecords VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setDouble(4, sal);
            System.out.println(ps.executeUpdate() + " row(s) inserted.");
        }
    }

    static void view(Connection con) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM EmployeeRecords")) {
            System.out.printf("%-6s %-15s %-12s %-10s%n", "ID", "Name", "Dept", "Salary");
            while (rs.next()) {
                System.out.printf("%-6d %-15s %-12s %-10.2f%n",
                    rs.getInt("emp_id"), rs.getString("name"),
                    rs.getString("department"), rs.getDouble("salary"));
            }
        }
    }

    static void update(Connection con, Scanner sc) throws SQLException {
        System.out.print("ID to update: ");  int id = sc.nextInt();
        System.out.print("New salary: ");    double sal = sc.nextDouble();

        String sql = "UPDATE EmployeeRecords SET salary = ? WHERE emp_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, sal);
            ps.setInt(2, id);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? n + " row(s) updated." : "No employee with that ID.");
        }
    }

    static void delete(Connection con, Scanner sc) throws SQLException {
        System.out.print("ID to delete: "); int id = sc.nextInt();

        String sql = "DELETE FROM EmployeeRecords WHERE emp_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? n + " row(s) deleted." : "No employee with that ID.");
        }
    }
}