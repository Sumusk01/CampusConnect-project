// File: StudentApp.java
import java.util.*;
import java.io.*;

public class StudentApp {
    // shared in-memory list used as fallback
    public static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile(); // load persisted file data (no clearing)
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Students");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Enter valid number!");
                continue;
            }

            if (choice == 1) {
                addStudent(sc);
            } else if (choice == 2) {
                viewStudents(sc);
            } else if (choice == 3) {
                searchStudent(sc);
            } else if (choice == 4) {
                deleteStudent(sc);
            } else if (choice == 5) {
                updateStudent(sc);
            } else if (choice == 6) {
                System.out.println("Exiting ....");
                break;
            } else {
                System.out.println("Invalid Choice!");
            }
        }
        sc.close();
    }

    static void addStudent(Scanner sc) {
        Student s = new Student();
        try {
            System.out.print("Enter ID: ");
            s.id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Name: ");
            s.name = sc.nextLine();
            if (s.name.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }
            for (Student st : students) {
                if (st.id == s.id) {
                    System.out.println("ID already exists!");
                    return;
                }
            }
            students.add(s);
            saveToFile();
            System.out.println("Student Added Successfully!");
        } catch (Exception e) {
            System.out.println("Invalid input!");
        }
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    static boolean addStudentFromUI(int id, String name, int age, String course, String email) {
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("ID already Exists!");
                return false;
            }
        }
        Student s = new Student(id, name, age, course, email);
        students.add(s);
        saveToFile();
        return true;
    }

    static void viewStudents(Scanner sc) {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        System.out.println("\n------------------------------------------------");
        System.out.println("          STUDENT RECORDS");
        System.out.println("--------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
            System.out.println("-----------------------------------------------");
        }
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    static void searchStudent(Scanner sc) {
        System.out.print("Enter ID to search: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean found = false;
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Found " + s);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found!");
        }
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean found = false;
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == id) {
                it.remove();
                saveToFile();
                System.out.println("Student deleted successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found!");
        }
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    static void updateStudent(Scanner sc) {
        System.out.print("Enter ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean found = false;
        for (Student s : students) {
            if (s.id == id) {
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();
                saveToFile();
                System.out.println("Student updated successfully!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found!");
        }
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    static void loadFromFile() {
        students.clear();
        try {
            java.io.File file = new java.io.File("students.txt");
            if (!file.exists()) return;
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                // defensive split: split on " , " but tolerate missing fields
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length < 2) continue; // skip malformed
                Student s = new Student();
                try {
                    s.id = Integer.parseInt(parts[0]);
                } catch (Exception ex) {
                    continue; // skip malformed id
                }
                s.name = parts.length > 1 ? parts[1] : "";
                s.age = parts.length > 2 ? parseIntSafe(parts[2]) : 0;
                s.course = parts.length > 3 ? parts[3] : "";
                s.email = parts.length > 4 ? parts[4] : "";
                students.add(s);
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading data!");
        }
    }

    static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    static void saveToFile() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter("students.txt");
            for (Student s : students) {
                writer.println(s.id + " , " + s.name + " , " + s.age + " , " + s.course + " , " + s.email);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving data!");
        }
    }
}
