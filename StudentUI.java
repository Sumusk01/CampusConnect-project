// File: StudentUI.java
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.sql.*;

public class StudentUI {
    private static StudentDAO dao;
    private static boolean dbAvailable = false;

    public static void main(String[] args) {
        // initialize DAO
        try {
            // match DBConnection settings or use direct values
            dao = new StudentDAO("jdbc:postgresql://localhost:5432/studentdb", "postgres", "89876@Sumuskan");
            dao.createTableIfNotExists();
            dbAvailable = true;
            System.out.println("DB available, using database storage.");
        } catch (Exception ex) {
            dbAvailable = false;
            System.err.println("DB not available, falling back to file storage: " + ex.getMessage());
        }

        // load file-based records into memory (fallback)
        StudentApp.loadFromFile();

        // build UI
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(600, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 2, 8, 8));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField emailField = new JTextField();

        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View Students");
        JButton searchBtn = new JButton("Search Student");
        JButton deleteBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");

        frame.add(new JLabel("Student ID: "));
        frame.add(idField);
        frame.add(new JLabel("Student Name: "));
        frame.add(nameField);
        frame.add(new JLabel("Age:"));
        frame.add(ageField);
        frame.add(new JLabel("Course:"));
        frame.add(courseField);
        frame.add(new JLabel("Email:"));
        frame.add(emailField);

        frame.add(addBtn);
        frame.add(viewBtn);
        frame.add(searchBtn);
        frame.add(deleteBtn);
        frame.add(updateBtn);

        frame.setVisible(true);

        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String course = courseField.getText().trim();
                String email = emailField.getText().trim();

                Student s = new Student(id, name, age, course, email);

                if (dbAvailable) {
                    try {
                        dao.addStudent(s);
                        JOptionPane.showMessageDialog(frame, "Student added to DB.");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "DB error: " + ex.getMessage());
                    }
                } else {
                    boolean ok = StudentApp.addStudentFromUI(id, name, age, course, email);
                    if (ok) JOptionPane.showMessageDialog(frame, "Student added to file storage.");
                    else JOptionPane.showMessageDialog(frame, "ID already exists in file storage.");
                }

                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                courseField.setText("");
                emailField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input: " + ex.getMessage());
            }
        });

        viewBtn.addActionListener(e -> {
            java.util.List<Student> list;
            if (dbAvailable) {
                try {
                    list = dao.getAllStudents();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "DB error: " + ex.getMessage());
                    return;
                }
            } else {
                list = StudentApp.students;
            }

            String[] columnNames = {"ID", "Name", "Age", "Course", "Email"};
            String[][] data = new String[list.size()][5];
            for (int i = 0; i < list.size(); i++) {
                Student s = list.get(i);
                data[i][0] = String.valueOf(s.id);
                data[i][1] = s.name;
                data[i][2] = String.valueOf(s.age);
                data[i][3] = s.course;
                data[i][4] = s.email;
            }
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            JFrame tableFrame = new JFrame("Student List");
            tableFrame.setSize(600, 300);
            tableFrame.add(scrollPane);
            tableFrame.setVisible(true);
        });

        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Student s = null;
                if (dbAvailable) {
                    s = dao.findById(id);
                } else {
                    for (Student st : StudentApp.students) {
                        if (st.id == id) {
                            s = st;
                            break;
                        }
                    }
                }
                if (s != null) {
                    JOptionPane.showMessageDialog(frame, s.toString());
                } else {
                    JOptionPane.showMessageDialog(frame, "Student not found!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid ID!");
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                boolean deleted = false;
                if (dbAvailable) {
                    deleted = dao.deleteStudent(id);
                } else {
                    Iterator<Student> it = StudentApp.students.iterator();
                    while (it.hasNext()) {
                        Student st = it.next();
                        if (st.id == id) {
                            it.remove();
                            deleted = true;
                            break;
                        }
                    }
                    if (deleted) StudentApp.saveToFile();
                }
                JOptionPane.showMessageDialog(frame, deleted ? "Deleted successfully!" : "Student not found!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid ID!");
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String course = courseField.getText().trim();
                String email = emailField.getText().trim();
                Student s = new Student(id, name, age, course, email);
                boolean updated = false;
                if (dbAvailable) {
                    updated = dao.updateStudent(s);
                } else {
                    for (Student st : StudentApp.students) {
                        if (st.id == id) {
                            st.name = name;
                            st.age = age;
                            st.course = course;
                            st.email = email;
                            updated = true;
                            break;
                        }
                    }
                    if (updated) StudentApp.saveToFile();
                }
                JOptionPane.showMessageDialog(frame, updated ? "Updated successfully!" : "Student not found!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });
    }
}
