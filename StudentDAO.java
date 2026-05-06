// File: StudentDAO.java
import java.sql.*;
import java.util.*;

public class StudentDAO {
    private final String url;
    private final String user;
    private final String password;

    public StudentDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                     "id INTEGER PRIMARY KEY," +
                     "name VARCHAR(200) NOT NULL," +
                     "age INTEGER," +
                     "course VARCHAR(200)," +
                     "email VARCHAR(200))";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (id, name, age, course, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, s.id);
            ps.setString(2, s.name);
            ps.setInt(3, s.age);
            ps.setString(4, s.course);
            ps.setString(5, s.email);
            ps.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT id, name, age, course, email FROM students ORDER BY id";
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course"),
                    rs.getString("email")
                ));
            }
        }
        return list;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT id, name, age, course, email FROM students WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getString("email")
                    );
                }
            }
        }
        return null;
    }

    public boolean updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name = ?, age = ?, course = ?, email = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.name);
            ps.setInt(2, s.age);
            ps.setString(3, s.course);
            ps.setString(4, s.email);
            ps.setInt(5, s.id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
