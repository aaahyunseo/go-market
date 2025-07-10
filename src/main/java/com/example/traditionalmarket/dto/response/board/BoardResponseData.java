package com.example.traditionalmarket.dto.response.board;

import com.example.traditionalmarket.dto.response.image.ImageData;
import com.example.traditionalmarket.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class BoardResponseData {
    private String marketName;
    private String address;
    private List<BoardResponseDto> boards;

    // 이미지가 없을 경우 대표 이미지
    private static final ImageData DEFAULT_IMAGE = new ImageData(
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            "https://gomarket-bucket.s3.ap-northeast-2.amazonaws.com/default-logo.png"
    );

    public static BoardResponseData of(String marketName, String address, List<Board> boards) {
        List<BoardResponseDto> dtos = boards.stream()
                .map(board -> {
                    List<ImageData> firstImage = board.getImageFiles().stream()
                            .findFirst()
                            .map(img -> List.of(new ImageData(img.getId(), img.getImageUrl())))
                            .orElse(List.of(DEFAULT_IMAGE));

                    return BoardResponseDto.builder()
                            .boardId(board.getId())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .createdAt(board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .author(board.getUser().getUserId())
                            .imageDataList(firstImage)
                            .likeCount(board.getLikeCount())
                            .dislikeCount(board.getDisLikeCount())
                            .build();
                })
                .collect(Collectors.toList());

        return new BoardResponseData(marketName, address, dtos);
    }
}
