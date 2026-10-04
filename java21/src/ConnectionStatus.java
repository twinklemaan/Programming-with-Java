import java.sql.*;

public class ConnectionStatus {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "root123";

        try (Connection con = DriverManager.getConnection(url, user, pass)) {

            // Connection status
            System.out.println("Connection status : " + (con.isClosed() ? "CLOSED" : "OPEN"));
            System.out.println("Valid connection  : " + con.isValid(2));

            // Details from DatabaseMetaData
            DatabaseMetaData md = con.getMetaData();
            System.out.println("Database product  : " + md.getDatabaseProductName());
            System.out.println("Server version    : " + md.getDatabaseProductVersion());
            System.out.println("Driver name       : " + md.getDriverName());
            System.out.println("Driver version    : " + md.getDriverVersion());
            System.out.println("URL               : " + md.getURL());
            System.out.println("Connected user    : " + md.getUserName());

            // Statement: run a query over the same connection
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT DATABASE()")) {
                if (rs.next()) {
                    System.out.println("Current database  : " + rs.getString(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection FAILED: " + e.getMessage());
        }
    }
}
