package com.example.traditionalmarket.dto.response.marketbook;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MarketBookResponseDto {
    private UUID marketId;
    private String marketName;
    private LocalDateTime visitedAt;
}
