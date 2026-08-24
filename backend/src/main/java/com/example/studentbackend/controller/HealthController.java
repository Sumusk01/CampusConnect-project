package com.example.studentbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        boolean dbUp = true;
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        } catch (Exception ex) {
            dbUp = false;
        }
        return Map.of("app", "student-backend", "db", dbUp);
    }

}
