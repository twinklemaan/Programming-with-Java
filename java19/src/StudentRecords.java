import java.sql.*;

public class StudentRecords {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "root123";
        String sql  = "SELECT id, name, branch, cgpa FROM student";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Connected to database.");
            System.out.printf("%-5s %-15s %-10s %-5s%n", "ID", "Name", "Branch", "CGPA");
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10s %-5.2f%n",
                    rs.getInt("id"), rs.getString("name"),
                    rs.getString("branch"), rs.getDouble("cgpa"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}