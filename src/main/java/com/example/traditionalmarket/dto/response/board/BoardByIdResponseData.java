package com.example.traditionalmarket.dto.response.board;

import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Reaction;
import com.example.traditionalmarket.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class BoardByIdResponseData {
    private String marketName;
    private UUID boardId;
    private String title;
    private String content;
    private String createdAt;
    private String author;
    private int likeCount;
    private int dislikeCount;
    private boolean liked;
    private boolean disliked;

    public static BoardByIdResponseData of(Board board, Reaction userReaction) {
        return BoardByIdResponseData.builder()
                .marketName(board.getMarket().getName())
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .author(board.getUser().getUserId())
                .likeCount(board.getLikeCount())
                .dislikeCount(board.getDisLikeCount())
                .liked(userReaction != null && userReaction.getReactionType() == ReactionType.LIKE)
                .disliked(userReaction != null && userReaction.getReactionType() == ReactionType.DISLIKE)
                .build();
    }
}

