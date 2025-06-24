package com.example.traditionalmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserRankingResponse {
    private List<UserRankingDto> rankings;
    private UserRankingDto myRank;
}
