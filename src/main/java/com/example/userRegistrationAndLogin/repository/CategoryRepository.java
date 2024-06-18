package com.example.userRegistrationAndLogin.repository;

import com.example.userRegistrationAndLogin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
