// File: DBConnection.java
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        // keep these values in sync with your DB; consider externalizing later
        String url = "jdbc:postgresql://localhost:5432/studentdb";
        String user = "postgres";
        String password = "89876@Sumuskan";
        return DriverManager.getConnection(url, user, password);
    }
}
