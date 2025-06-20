package com.example.traditionalmarket.service;

import com.example.traditionalmarket.authentication.AccessTokenProvider;
import com.example.traditionalmarket.authentication.PasswordHashEncryption;
import com.example.traditionalmarket.dto.TokenResponseDto;
import com.example.traditionalmarket.dto.request.auth.LoginDto;
import com.example.traditionalmarket.dto.request.auth.SignupDto;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordHashEncryption passwordHashEncryption;
    private final UserRepository userRepository;
    private final AccessTokenProvider accessTokenProvider;
    private final UserService userService;

    public TokenResponseDto signup(SignupDto signupDto) {

        userService.validateIsDuplicatedName(signupDto.getName());

        userService.validateIsDuplicatedUserId(signupDto.getUserId());

        String plainPassword = signupDto.getPassword();
        String hashedPassword = passwordHashEncryption.encrypt(plainPassword);

        User newUser = User.builder()
                .userId(signupDto.getUserId())
                .password(hashedPassword)
                .name(signupDto.getName())
                .build();
        userRepository.save(newUser);

        return createToken(newUser);
    }

    public TokenResponseDto login(LoginDto loginDto) {
        User user = userService.findExistingUserByUserId(loginDto.getUserId());

        userService.validateIsPasswordMatches(loginDto.getPassword(), user.getPassword());

        return createToken(user);
    }

    private TokenResponseDto createToken(User user) {
        String payload = String.valueOf(user.getId());
        String accessToken = accessTokenProvider.createToken(payload);

        return new TokenResponseDto(accessToken);
    }
}
