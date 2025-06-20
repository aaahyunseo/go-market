package com.example.traditionalmarket.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // BadRequestException

    // UnauthorizedException
    INVALID_TOKEN("4010", "유효하지 않은 토큰입니다."),
    INVALID_EMAIL_OR_PASSWORD("4011", "아이디 또는 비밀번호를 잘못 입력했습니다."),

    // ForbiddenException
    NO_ACCESS("4030", "접근 권한이 없습니다."),

    // NotFoundException
    COOKIE_NOT_FOUND("4040", "쿠키를 찾을 수 없습니다."),
    USER_NOT_FOUND("4041", "유저를 찾을 수 없습니다."),
    MARKET_NAME_NOT_FOUND("4042", "시장 이름을 찾을 수 없습니다."),
    MARKET_LOC_NOT_FOUND("4043", "인근 시장 위치를 찾을 수 없습니다."),

    // ConflictException
    DUPLICATED_NAME("4090", "이미 사용중인 이름입니다."),
    DUPLICATED_ID("4091", "이미 사용중인 아이디입니다."),
    DUPLICATED_MARKET("4092", "이미 방문한 장소입니다."),

    // ValidationException
    NOT_NULL("9001", "필수값이 누락되었습니다."),
    NOT_BLANK("9002", "필수값이 빈 값이거나 공백으로 되어있습니다."),
    REGEX("9003", "이메일 형식에 맞지 않습니다."),
    LENGTH("9004", "길이가 유효하지 않습니다.");

    private final String code;
    private final String message;

    // Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> REGEX;
            case "Length" -> LENGTH;
            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
