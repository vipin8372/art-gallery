package com.example.userRegistrationAndLogin.entity;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;


@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullname;
    private String gender;
    private String email;
    private String password;
    private String role; // New field for user role

    // Constructors
    public User() {
    }

    public User(String fullname, String gender, String email, String password, String role) {
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HttpSession getSession() {
        return null;
    }
}