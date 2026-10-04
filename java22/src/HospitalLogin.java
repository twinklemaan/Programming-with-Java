import java.sql.*;
import java.util.Scanner;

public class HospitalLogin {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Staff ID: ");
        String id = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT name, role FROM hospital_staff WHERE staff_id = ? AND password = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    System.out.println("Authentication successful. Welcome, " + name + " (" + role + ").");

                    switch (role) {
                        case "Doctor":
                            System.out.println("Access granted: patient records, prescriptions, diagnosis.");
                            break;
                        case "Nurse":
                            System.out.println("Access granted: patient vitals, ward schedule.");
                            break;
                        default:
                            System.out.println("Access granted: administration and staff management.");
                    }
                } else {
                    System.out.println("Access denied: invalid Staff ID or password.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}