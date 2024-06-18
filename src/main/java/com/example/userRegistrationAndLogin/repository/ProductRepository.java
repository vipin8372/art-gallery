package com.example.userRegistrationAndLogin.repository;

import com.example.userRegistrationAndLogin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String categoryName);
}
