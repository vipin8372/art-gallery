package com.example.userRegistrationAndLogin.repository;

import com.example.userRegistrationAndLogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    void deleteById(long id);






}
