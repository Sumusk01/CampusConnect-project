// File: Student.java
public class Student {
    public int id;
    public String name;
    public int age;
    public String course;
    public String email;

    public Student() {}

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(int id, String name, int age, String course, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Age: " + age + " | Course: " + course + " | Email: " + email;
    }
}
