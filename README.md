# Student Information Management System

**Project Type** Full Stack Web Application  
**Technologies Used** Java · Spring Boot · PostgreSQL · React · Vite · Axios · Maven · Git

## Overview
The Student Information Management System is a full‑stack web application for managing student records. It demonstrates end‑to‑end CRUD operations with a Spring Boot REST API, PostgreSQL persistence, and a React frontend. The app includes health monitoring endpoints, validation, and a responsive UI for adding, editing, deleting, and listing students.

## Features
- Create, Read, Update, Delete student records
- RESTful API with Spring Boot
- PostgreSQL database for persistent storage
- React frontend with Vite for fast development
- Health endpoint for service monitoring
- Client and server validation, error handling

## Architecture
- **Backend** Spring Boot application exposing `/api/*` endpoints
- **Database** PostgreSQL
- **Frontend** React + Vite calling backend via a dev proxy (`/api`)

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6+
- Node.js 18+ and npm
- PostgreSQL

### Backend Setup
1. Create a PostgreSQL database:
```sql
CREATE DATABASE studentdb;
CREATE USER student_user WITH ENCRYPTED PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE studentdb TO student_user;