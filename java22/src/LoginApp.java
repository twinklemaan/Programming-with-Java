import java.sql.*;
import java.util.Scanner;

public class LoginApp {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT username FROM users WHERE username = ? AND password = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Login successful. Welcome, " + rs.getString("username") + "!");
                } else {
                    System.out.println("Login failed: invalid username or password.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}