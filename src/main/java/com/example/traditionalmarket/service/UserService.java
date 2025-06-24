package com.example.traditionalmarket.service;

import com.example.traditionalmarket.authentication.PasswordHashEncryption;
import com.example.traditionalmarket.dto.request.user.DeleteUserDto;
import com.example.traditionalmarket.dto.response.UserRankingDto;
import com.example.traditionalmarket.dto.response.UserRankingResponse;
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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VisitedMarketRepository visitedMarketRepository;
    private final PasswordHashEncryption passwordHashEncryption;

    // 유저 개인 정보 조회
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

    // 랭킹 조회
    public UserRankingResponse getUserRanking(User currentUser) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getMarketBook() != null)
                .toList();

        List<UserRankingDto> sortedList = users.stream()
                .map(u -> {
                    Long count = visitedMarketRepository.countByMarketBook(u.getMarketBook());
                    return UserRankingDto.builder()
                            .rank(0)
                            .userName(u.getName())
                            .visitMarketCount(count != null ? count.intValue() : 0)
                            .build();
                })
                .sorted(Comparator.comparingInt(UserRankingDto::getVisitMarketCount).reversed())
                .collect(Collectors.toList());

        int rank = 1;
        long previousCount = -1;
        int actualRank = 1;

        for (int i = 0; i < sortedList.size(); i++) {
            UserRankingDto dto = sortedList.get(i);
            if (dto.getVisitMarketCount() != previousCount) {
                actualRank = rank;
            }
            sortedList.set(i, UserRankingDto.builder()
                    .rank(actualRank)
                    .userName(dto.getUserName())
                    .visitMarketCount(dto.getVisitMarketCount())
                    .build());
            previousCount = dto.getVisitMarketCount();
            rank++;
        }

        UserRankingDto myRank = sortedList.stream()
                .filter(dto -> dto.getUserName().equals(currentUser.getName()))
                .findFirst()
                .orElse(UserRankingDto.builder()
                        .rank(0)
                        .userName(currentUser.getName())
                        .visitMarketCount(0)
                        .build());

        return new UserRankingResponse(sortedList, myRank);
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
