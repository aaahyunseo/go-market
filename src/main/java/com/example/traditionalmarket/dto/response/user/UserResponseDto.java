package com.example.traditionalmarket.dto.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private String userId;
    private String name;
    private Long visitMarketCount;
    private Long rank;
}
