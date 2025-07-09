package com.example.traditionalmarket.dto.response.board;

import com.example.traditionalmarket.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class BoardResponseData {
    private String marketName;
    private List<BoardResponseDto> boards;

    public static BoardResponseData of(String marketName, List<Board> boards) {
        List<BoardResponseDto> dtos = boards.stream()
                .map(board -> BoardResponseDto.builder()
                        .boardId(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .author(board.getUser().getUserId())
                        .likeCount(board.getLikeCount())
                        .dislikeCount(board.getDisLikeCount())
                        .build())
                .collect(Collectors.toList());

        return new BoardResponseData(marketName, dtos);
    }
}
