package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.comment.CommentRequestDto;
import com.example.traditionalmarket.dto.response.comment.CommentListData;
import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Comment;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.exception.ForbiddenException;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.BoardRepository;
import com.example.traditionalmarket.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 특정 게시글 댓글 목록 조회하기
    public CommentListData getComments(UUID boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
        List<Comment> comments = commentRepository.findAllByBoardOrderByCreatedAtDesc(board);
        return CommentListData.of(comments);
    }

    // 댓글 작성하기
    public void createComment(User user, UUID boardId, CommentRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .user(user)
                .board(board)
                .build();

        commentRepository.save(comment);
    }

    // 댓글 삭제하기
    public void deleteComment(User user, UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(ErrorCode.NO_ACCESS);
        }

        commentRepository.delete(comment);
    }
}
