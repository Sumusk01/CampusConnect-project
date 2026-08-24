package com.example.studentbackend.repository;

import com.example.studentbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
public interface StudentRepository extends JpaRepository<Student, Long> {
}