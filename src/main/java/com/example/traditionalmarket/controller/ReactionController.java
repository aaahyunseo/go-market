package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    // 좋아요 누르기
    @Operation(summary = "좋아요 누르기", description = "게시글에 좋아요를 추가합니다.")
    @PostMapping("/like/{boardId}")
    public ResponseEntity<ResponseDto<Void>> likeBoard(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        reactionService.likeBoard(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "좋아요 추가 성공"), HttpStatus.CREATED);
    }

    // 싫어요 누르기
    @Operation(summary = "싫어요 누르기", description = "게시글에 싫어요를 추가합니다.")
    @PostMapping("/dislike/{boardId}")
    public ResponseEntity<ResponseDto<Void>> dislikeBoard(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        reactionService.dislikeBoard(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "싫어요 추가 성공"), HttpStatus.CREATED);
    }

    // 좋아요 취소하기
    @Operation(summary = "좋아요 취소하기", description = "게시글에 누른 좋아요를 취소합니다.")
    @DeleteMapping("/like/{boardId}")
    public ResponseEntity<ResponseDto<Void>> deleteLike(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        reactionService.deleteLike(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "좋아요 취소 성공"), HttpStatus.OK);
    }

    // 싫어요 취소하기
    @Operation(summary = "싫어요 취소하기", description = "게시글에 누른 싫어요를 취소합니다.")
    @DeleteMapping("/dislike/{boardId}")
    public ResponseEntity<ResponseDto<Void>> deleteDisLike(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        reactionService.deleteDislike(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "싫어요 취소 성공"), HttpStatus.OK);
    }

}
