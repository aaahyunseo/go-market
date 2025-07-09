package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Reaction;
import com.example.traditionalmarket.entity.ReactionType;
import com.example.traditionalmarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
    Optional<Reaction> findByUserAndBoardAndReactionType(User user, Board board, ReactionType reactionType);
    boolean existsByUserAndBoardAndReactionType(User user, Board board, ReactionType reactionType);
    Optional<Reaction> findByUserAndBoard(User user, Board board);
}
