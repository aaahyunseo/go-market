package com.example.traditionalmarket.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRankingDto {
    private int rank;
    private String userName;
    private int visitMarketCount;
}
