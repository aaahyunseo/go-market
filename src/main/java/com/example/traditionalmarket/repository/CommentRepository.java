package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByBoardOrderByCreatedAtDesc(Board board);
}
