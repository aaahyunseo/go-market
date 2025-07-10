package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.board.CreateBoardDto;
import com.example.traditionalmarket.dto.request.board.UpdateBoardDto;
import com.example.traditionalmarket.dto.response.board.BoardByIdResponseData;
import com.example.traditionalmarket.dto.response.board.BoardResponseData;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    public final BoardService boardService;

    // 게시글 전체 조회하기
    @Operation(summary = "시장별 게시글 전체 조회하기", description = "특정 시장 게시글 목록을 전체 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<BoardResponseData>> getAllBoards(@RequestParam(name = "marketName") String marketName) {
        BoardResponseData boardResponseData = boardService.getAllBoards(marketName);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "시장 게시글 전체 조회 성공", boardResponseData), HttpStatus.OK);
    }

    // 게시글 상세 조회하기
    @Operation(summary = "게시글 상세 조회하기", description = "특정 게시글을 상세 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDto<BoardByIdResponseData>> getBoard(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        BoardByIdResponseData boardByIdResponseData = boardService.getBoardById(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 상세 조회 성공", boardByIdResponseData), HttpStatus.OK);
    }

    // 게시글 작성하기
    @Operation(summary = "게시글 작성하기", description = "게시글을 작성합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createBoard(@AuthenticatedUser User user,
                                                         @RequestParam(name = "marketName") String marketName,
                                                         @ModelAttribute CreateBoardDto createBoardDto,
                                                         @RequestPart(value = "image", required = false) List<MultipartFile> images) throws IOException {
        boardService.createBoard(user, marketName, createBoardDto, images);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 작성 성공"), HttpStatus.OK);
    }

    // 게시글 수정하기
    @Operation(summary = "게시글 수정하기", description = "특정 게시글 내용을 수정합니다.")
    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> updateBoard(@AuthenticatedUser User user,
                                                         @PathVariable UUID boardId,
                                                         @ModelAttribute UpdateBoardDto updateBoardDto,
                                                         @RequestPart(value = "image", required = false) List<MultipartFile> images,
                                                         @RequestParam(value = "deleteImageIds", required = false) List<UUID> imageIdsToDelete) throws IOException {
        boardService.updateBoard(user, boardId, updateBoardDto, images, imageIdsToDelete);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 수정 성공"), HttpStatus.OK);
    }

    // 게시글 삭제하기
    @Operation(summary = "게시글 삭제하기", description = "특정 게시글을 삭제합니다.")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDto<Void>> deleteBoard(@AuthenticatedUser User user, @PathVariable UUID boardId) {
        boardService.deleteBoard(user, boardId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "게시글 삭제 성공"), HttpStatus.OK);
    }

}
