import java.sql.*;

public class ResultSetNavigation {
    static final String URL  = "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASS = "root123";

    static void show(String label, ResultSet rs) throws SQLException {
        System.out.printf("%-22s -> row %d: %d | %s | %s | %.2f%n",
            label, rs.getRow(), rs.getInt("emp_id"), rs.getString("name"),
            rs.getString("department"), rs.getDouble("salary"));
    }

    public static void main(String[] args) {
        String sql = "SELECT emp_id, name, department, salary FROM EmployeeRecords ORDER BY emp_id";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement(
                 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Cursor before first row? " + rs.isBeforeFirst());

            System.out.println("\n--- Forward: next() ---");
            while (rs.next()) {
                show("next()", rs);
            }
            System.out.println("Cursor after last row?  " + rs.isAfterLast());

            System.out.println("\n--- Backward: previous() ---");
            while (rs.previous()) {
                show("previous()", rs);
            }

            System.out.println("\n--- Jumping ---");
            if (rs.first())       show("first()", rs);
            if (rs.last())        show("last()", rs);
            if (rs.absolute(2))   show("absolute(2)", rs);
            if (rs.relative(2))   show("relative(+2)", rs);
            if (rs.relative(-1))  show("relative(-1)", rs);
            if (rs.absolute(-1))  show("absolute(-1) = last", rs);

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
