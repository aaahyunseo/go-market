package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.TokenResponseDto;
import com.example.traditionalmarket.dto.request.auth.LoginDto;
import com.example.traditionalmarket.dto.request.auth.SignupDto;
import com.example.traditionalmarket.service.AuthService;
import com.example.traditionalmarket.service.CookieService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;

    @Operation(summary = "회원가입", description = "회원가입을 위해 유저의 아이디, 비밀번호, 이름을 입력해야 합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<TokenResponseDto>> signup(@RequestBody @Valid SignupDto signupDto, HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = authService.signup(signupDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "회원가입 완료", tokenResponseDto), HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "로그인을 하기 위해 유저의 아이디, 비밀번호를 입력해야 합니다.")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenResponseDto>> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        TokenResponseDto tokenResponseDto = authService.login(loginDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그인 완료", tokenResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "계정이 로그아웃 됩니다.")
    @GetMapping("/logout")
    public ResponseEntity<ResponseDto<Void>> logout(final HttpServletResponse response) {
        clearCookies(response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그아웃 완료"), HttpStatus.OK);
    }

    private void clearCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader("Set-Cookie", accessCookie.toString());
    }
}
