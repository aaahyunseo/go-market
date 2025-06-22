package com.example.traditionalmarket.service;

import com.example.traditionalmarket.authentication.PasswordHashEncryption;
import com.example.traditionalmarket.dto.request.user.DeleteUserDto;
import com.example.traditionalmarket.dto.response.UserResponseDto;
import com.example.traditionalmarket.entity.MarketBook;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.exception.ConflictException;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.UnauthorizedException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.UserRepository;
import com.example.traditionalmarket.repository.VisitedMarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VisitedMarketRepository visitedMarketRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    public UserResponseDto getUserInfo(User user) {
        MarketBook marketBook = user.getMarketBook();
        Long visitedCount = visitedMarketRepository.countByMarketBook(marketBook);

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .visitMarketCount(visitedCount)
                .build();
        return userResponseDto;
    }

    public void deleteUser(User user, DeleteUserDto deleteUserDto) {
        validateIsPasswordMatches(deleteUserDto.getPassword(), user.getPassword());
        userRepository.delete(user);
    }

    public void validateIsPasswordMatches(String requestedPassword, String userPassword) {
        if (!passwordHashEncryption.matches(requestedPassword, userPassword)) {
            throw new UnauthorizedException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }
    }

    public void validateIsDuplicatedUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new ConflictException(ErrorCode.DUPLICATED_ID);
        }
    }

    public void validateIsDuplicatedName(String name) {
        if (userRepository.existsByName(name)) {
            throw new ConflictException(ErrorCode.DUPLICATED_NAME);
        }
    }

    public User findExistingUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_EMAIL_OR_PASSWORD));
    }
}
