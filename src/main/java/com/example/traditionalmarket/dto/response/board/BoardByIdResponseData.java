package com.example.traditionalmarket.dto.response.board;

import com.example.traditionalmarket.dto.response.image.ImageData;
import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Reaction;
import com.example.traditionalmarket.entity.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private List<ImageData> imageDataList;
    private int likeCount;
    private int dislikeCount;
    private boolean liked;
    private boolean disliked;

    public static BoardByIdResponseData of(Board board, Reaction userReaction) {
        List<ImageData> images = board.getImageFiles().stream()
                .map(imageFile -> new ImageData(imageFile.getId(), imageFile.getImageUrl()))
                .toList();

        return BoardByIdResponseData.builder()
                .marketName(board.getMarket().getName())
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .author(board.getUser().getUserId())
                .imageDataList(images)
                .likeCount(board.getLikeCount())
                .dislikeCount(board.getDisLikeCount())
                .liked(userReaction != null && userReaction.getReactionType() == ReactionType.LIKE)
                .disliked(userReaction != null && userReaction.getReactionType() == ReactionType.DISLIKE)
                .build();
    }
}

