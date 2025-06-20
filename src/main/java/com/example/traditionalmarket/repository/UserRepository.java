package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserId(String userId);
    boolean existsByName(String name);
    boolean existsByUserId(String userId);
}
