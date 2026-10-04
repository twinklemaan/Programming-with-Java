
import java.sql.*;

public class ProductDetails {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String pass = "root123";
        String sql  = "SELECT product_id, product_name, quantity, price FROM product";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.printf("%-12s %-15s %-10s %-10s%n", "Product ID", "Name", "Quantity", "Price");
            while (rs.next()) {
                System.out.printf("%-12d %-15s %-10d %-10.2f%n",
                    rs.getInt("product_id"), rs.getString("product_name"),
                    rs.getInt("quantity"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}