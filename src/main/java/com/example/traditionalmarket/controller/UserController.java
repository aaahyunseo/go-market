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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
