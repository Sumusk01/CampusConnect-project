package com.example.studentbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;

@Component
public class BootstrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public BootstrapData(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @SuppressWarnings("null")
    @Override
    public void run(String... args) throws Exception {
        // Let JPA generate IDs for bootstrap data.
        Student s1 = new Student(null, "Sourav Paitandy", 23, "CSE", "sp.22@nshm.edu.in");
        Student s2 = new Student(null, "Sumit Kumar", 22, "CSE", "sk.22@nshm.edu.in");

        if (studentRepository.findAll().isEmpty()) {
    studentRepository.saveAll(List.of(s1, s2));
}
        System.out.println("Bootstrap data loaded");
    }
}