package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.comment.CommentRequestDto;
import com.example.traditionalmarket.dto.response.comment.CommentListData;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    // 특정 게시글 댓글 목록 조회하기
    @Operation(summary = "게시글 댓글 목록 조회하기", description = "특정 시장 게시글 댓글 목록을 전체 조회합니다.")
    @GetMapping("/get/{boardId}")
    public ResponseEntity<ResponseDto<CommentListData>> getComments(@PathVariable UUID boardId) {
        CommentListData commandListData = commentService.getComments(boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "댓글 전체 조회 완료", commandListData), HttpStatus.OK);
    }

    // 댓글 작성하기
    @Operation(summary = "게시글 댓글 작성하기", description = "특정 시장 게시글에 댓글을 작성합니다.")
    @PostMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> createComment(@Parameter(hidden = true) @AuthenticatedUser User user, @PathVariable UUID boardId, @RequestBody CommentRequestDto dto) {
        commentService.createComment(user, boardId, dto);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.CREATED, "댓글 작성 완료", null));
    }

    // 댓글 삭제하기
    @Operation(summary = "게시글 댓글 삭제하기", description = "특정 시장 게시글에 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(@Parameter(hidden = true) @AuthenticatedUser User user, @PathVariable UUID commentId) {
        commentService.deleteComment(user, commentId);
        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, "댓글 삭제 완료", null));
    }
}
