package com.example.userRegistrationAndLogin.repository;

import com.example.userRegistrationAndLogin.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
