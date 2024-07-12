# Task-Management
An application designed to streamline task assignment, tracking, and completion.

## Overview

This Task Management project is built with Spring Boot, React, and JWT-based authentication to manage tasks and users efficiently. The application supports various roles (Admin, Project Manager, Team Lead, Member) with specific permissions.

## Features

- User authentication and authorization with JWT
- Role-based access control
- Task creation, assignment, updating, and deletion
- User management
- Token blacklisting for secure logout

## Project Structure

### Backend

- **Spring Boot** for the backend framework
- **Spring Security** for authentication and authorization
- **JWT** for token-based authentication
- **JPA** for database interaction

### Frontend

- **React** for building user interfaces

## Endpoints

### Authentication

- **Signup**: `POST /api/auth/signup`
- **Login**: `POST /api/auth/login`
- **Logout**: `POST /api/auth/logout`

### User Management

- **Get All Users**: `GET /api/users` (Admin)
- **Get User by ID**: `GET /api/users/{id}` (Admin)
- **Update User**: `PUT /api/users/{id}` (Admin)
- **Delete User**: `DELETE /api/users/{id}` (Admin)
- **Change User Password**: `PATCH /api/users/{id}/password` (Admin, Member)

### Task Management

- **Create Task**: `POST /api/tasks` (Admin, Project Manager)
- **Get Task by ID**: `GET /api/tasks/{id}` (Authenticated)
- **Get All Tasks**: `GET /api/tasks` (Admin, Project Manager, Team Lead)
- **Get Tasks by User ID**: `GET /api/tasks/user/{id}` (Member)
- **Update Task**: `PUT /api/tasks/{id}` (Admin, Project Manager)
- **Delete Task**: `DELETE /api/tasks/{id}` (Admin)
- **Assign Task**: `PATCH /api/tasks/{id}/assign` (Admin, Project Manager, Team Lead)
- **Unassign Task**: `PATCH /api/tasks/{id}/unassign` (Admin, Project Manager, Team Lead)
- **Update Task Status**: `PATCH /api/tasks/{id}/status` (Authenticated)
- **Update Task Priority**: `PATCH /api/tasks/{id}/priority` (Admin, Project Manager, Team Lead)
- **Update Task Due Date**: `PATCH /api/tasks/{id}/dueDate` (Admin, Project Manager, Team Lead)

## Getting Started

### Prerequisites

- Java 11+
- Maven
- MySQL (or any other DBMS)
- Postman

### Backend Setup

1. Clone the repository:
   git clone https://github.com/samuelkhiba3/task-management.git
   cd task-management/backend
2. Configure the database in application.properties.
   spring.datasource.url=jdbc:mysql://localhost:3306/your_schema_name
   spring.datasource.username=root spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
4. Build and run the backend:
  mvn clean install
  mvn spring-boot:run
