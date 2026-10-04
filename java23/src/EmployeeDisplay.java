import java.sql.*;

public class EmployeeDisplay {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) {
        String sql = "SELECT emp_id, name, department, salary FROM EmployeeRecords ORDER BY emp_id";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("Record " + count);
                System.out.println("  ID         : " + rs.getInt("emp_id"));
                System.out.println("  Name       : " + rs.getString("name"));
                System.out.println("  Department : " + rs.getString("department"));
                System.out.println("  Salary     : " + rs.getDouble("salary"));
                System.out.println();
            }
            if (count == 0) {
                System.out.println("No employee records found.");
            } else {
                System.out.println("Total records displayed: " + count);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
