package com.example.traditionalmarket.service;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Reaction;
import com.example.traditionalmarket.entity.ReactionType;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.exception.ConflictException;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.BoardRepository;
import com.example.traditionalmarket.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final BoardRepository boardRepository;

    // 좋아요 누르기
    public void likeBoard(User user, UUID boardId) {
        Board board = getBoard(boardId);

        // 이미 같은 유저가 싫어요 눌렀으면 취소
        reactionRepository.findByUserAndBoard(user, board)
                .ifPresent(reaction -> {
                    if (reaction.getReactionType() == ReactionType.DISLIKE) {
                        board.setDisLikeCount(board.getDisLikeCount() - 1);
                        reactionRepository.delete(reaction);
                    }
                });

        // 이미 좋아요 눌렀으면 무시
        boolean alreadyLiked = reactionRepository.existsByUserAndBoardAndReactionType(user, board, ReactionType.LIKE);
        if (alreadyLiked) throw new ConflictException(ErrorCode.DUPLICATED_LIKE);

        Reaction reaction = Reaction.builder()
                .user(user)
                .board(board)
                .reactionType(ReactionType.LIKE)
                .build();

        board.setLikeCount(board.getLikeCount() + 1);
        reactionRepository.save(reaction);
    }

    // 싫어요 누르기
    public void dislikeBoard(User user, UUID boardId) {
        Board board = getBoard(boardId);

        // 이미 같은 유저가 좋아요 눌렀으면 취소
        reactionRepository.findByUserAndBoard(user, board)
                .ifPresent(reaction -> {
                    if (reaction.getReactionType() == ReactionType.LIKE) {
                        board.setLikeCount(board.getLikeCount() - 1);
                        reactionRepository.delete(reaction);
                    }
                });

        // 이미 싫어요 눌렀으면 무시
        boolean alreadyDisliked = reactionRepository.existsByUserAndBoardAndReactionType(user, board, ReactionType.DISLIKE);
        if (alreadyDisliked) throw new ConflictException(ErrorCode.DUPLICATED_DISLIKE);

        Reaction reaction = Reaction.builder()
                .user(user)
                .board(board)
                .reactionType(ReactionType.DISLIKE)
                .build();

        board.setDisLikeCount(board.getDisLikeCount() + 1);
        reactionRepository.save(reaction);
    }

    // 좋아요 취소하기
    public void deleteLike(User user, UUID boardId) {
        Board board = getBoard(boardId);

        Reaction reaction = reactionRepository.findByUserAndBoardAndReactionType(user, board, ReactionType.LIKE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REACTION_NOT_FOUND));

        board.setLikeCount(board.getLikeCount() - 1);
        reactionRepository.delete(reaction);
    }

    // 싫어요 취소하기
    public void deleteDislike(User user, UUID boardId) {
        Board board = getBoard(boardId);

        Reaction reaction = reactionRepository.findByUserAndBoardAndReactionType(user, board, ReactionType.DISLIKE)
                .orElseThrow(() -> new NotFoundException(ErrorCode.REACTION_NOT_FOUND));

        board.setDisLikeCount(board.getDisLikeCount() - 1);
        reactionRepository.delete(reaction);
    }

    private Board getBoard(UUID boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }
}
