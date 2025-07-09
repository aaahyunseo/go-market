package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    List<Board> findAllByMarketNameOrderByCreatedAtDesc(String marketName);
    Optional<Board> findById(UUID id);
    Optional<Board> findByIdAndUser(UUID id, User user);
}
