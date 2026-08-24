package com.example.studentbackend.service;

import com.example.studentbackend.model.Student;
import com.example.studentbackend.repository.StudentRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<Student> findById(@NonNull Long id) {
        return repo.findById(id);
    }

    public Student save(@NonNull Student s) {
        return repo.save(s);
    }

    public void deleteById(@NonNull Long id) {
        repo.deleteById(id);
    }
}