package com.example.traditionalmarket.dto.response.board;

import com.example.traditionalmarket.dto.response.image.ImageData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class BoardResponseDto {
    private UUID boardId;
    private String title;
    private String content;
    private String createdAt;
    private String author;
    private List<ImageData> imageDataList;
    private int likeCount;
    private int dislikeCount;
}
