package com.example.traditionalmarket.dto.response.board;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BoardResponseDto {
    private UUID boardId;
    private String title;
    private String content;
    private String createdAt;
    private String author;
    private int likeCount;
    private int dislikeCount;
}
