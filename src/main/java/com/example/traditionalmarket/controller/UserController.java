package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.response.user.UserRankingResponse;
import com.example.traditionalmarket.dto.response.user.UserResponseDto;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 정보 조회", description = "유저의 아이디, 이름, 방문 시장 갯수 정보가 조회됩니다.")
    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserInfo(@AuthenticatedUser User user) {
        UserResponseDto userResponseDto = userService.getUserInfo(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "유저 정보 조회 완료", userResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "유저 랭킹 조회", description = "모든 유저의 랭킹과 로그인한 유저의 랭킹이 조회됩니다.")
    @GetMapping("/rank")
    public ResponseEntity<ResponseDto<UserRankingResponse>> getUserRank(@AuthenticatedUser User user) {
        UserRankingResponse response = userService.getUserRanking(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "유저 랭킹 조회 완료", response), HttpStatus.OK);
    }

    @Operation(summary = "유저 프로필 사진 등록", description = "유저의 프로필 사진을 등록합니다")
    @PostMapping("/profile")
    public ResponseEntity<ResponseDto<Void>> uploadProfileImg(@AuthenticatedUser User user,
                                                              @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        userService.uploadProfileImage(user, image);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "유저 프로필 사진 등록 완료"), HttpStatus.CREATED);
    }

    @Operation(summary = "유저 프로필 사진 삭제", description = "유저의 프로필 사진을 삭제합니다")
    @DeleteMapping("/profile")
    public ResponseEntity<ResponseDto<Void>> deleteProfileImg(@AuthenticatedUser User user) throws IOException {
        userService.deleteProfileImage(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "유저 프로필 사진 삭제 완료"), HttpStatus.OK);
    }
}
