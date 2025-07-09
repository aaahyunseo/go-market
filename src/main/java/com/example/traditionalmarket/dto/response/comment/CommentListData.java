package com.example.traditionalmarket.dto.response.comment;

import com.example.traditionalmarket.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommentListData {
    private List<CommentDto> commentList;

    public static CommentListData of(List<Comment> comments) {
        return CommentListData.builder()
                .commentList(comments.stream()
                        .map(CommentDto::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
