import java.sql.*;
import java.util.Scanner;

public class StudentCRUD {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connected to database.");
            int ch;
            do {
                System.out.println("\n1.Create 2.Read 3.Update 4.Delete 5.Exit");
                System.out.print("Choice: ");
                ch = sc.nextInt();
                switch (ch) {
                    case 1: create(con, sc); break;
                    case 2: read(con); break;
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

    static void create(Connection con, Scanner sc) throws SQLException {
        System.out.print("Roll no: "); int roll = sc.nextInt();
        System.out.print("Name: ");    String name = sc.next();
        System.out.print("Course: ");  String course = sc.next();
        System.out.print("Marks: ");   int marks = sc.nextInt();

        String sql = "INSERT INTO studentrecords VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setInt(4, marks);
            System.out.println(ps.executeUpdate() + " row(s) inserted.");
        }
    }

    static void read(Connection con) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM studentrecords")) {
            System.out.printf("%-8s %-15s %-12s %-6s%n", "Roll", "Name", "Course", "Marks");
            while (rs.next()) {
                System.out.printf("%-8d %-15s %-12s %-6d%n",
                    rs.getInt("roll_no"), rs.getString("name"),
                    rs.getString("course"), rs.getInt("marks"));
            }
        }
    }

    static void update(Connection con, Scanner sc) throws SQLException {
        System.out.print("Roll no to update: "); int roll = sc.nextInt();
        System.out.print("New marks: ");         int marks = sc.nextInt();

        String sql = "UPDATE studentrecords SET marks = ? WHERE roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, marks);
            ps.setInt(2, roll);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? n + " row(s) updated." : "No student with that roll no.");
        }
    }

    static void delete(Connection con, Scanner sc) throws SQLException {
        System.out.print("Roll no to delete: "); int roll = sc.nextInt();

        String sql = "DELETE FROM studentrecords WHERE roll_no = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roll);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? n + " row(s) deleted." : "No student with that roll no.");
        }
    }
}
