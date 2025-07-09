package com.example.traditionalmarket.dto.response.comment;

import com.example.traditionalmarket.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
public class CommentDto {
    private UUID commentId;
    private String userId;
    private String content;
    private String createdAt;

    public static CommentDto of(Comment comment) {
        return CommentDto.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
