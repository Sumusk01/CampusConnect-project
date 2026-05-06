// File: TestDBConnection.java
import java.sql.*;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            // ensure driver class is loaded
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/studentdb";
            String user = "postgres";
            String pass = "89876@Sumuskan";

            try (Connection c = DriverManager.getConnection(url, user, pass)) {
                System.out.println("Connected to DB successfully.");
            }
        } catch (ClassNotFoundException cnf) {
            System.err.println("Driver class not found: " + cnf.getMessage());
        } catch (SQLException sqle) {
            System.err.println("SQL error: " + sqle.getMessage());
        } catch (Exception e) {
            System.err.println("Other error: " + e.getMessage());
        }
    }
}
