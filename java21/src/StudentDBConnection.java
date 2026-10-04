import java.sql.*;

public class StudentDBConnection {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String pass = "root12";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            if (con != null && !con.isClosed()) {
                System.out.println("Connection status: SUCCESS");
                System.out.println("Student database is connected successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Connection status: FAILED");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}