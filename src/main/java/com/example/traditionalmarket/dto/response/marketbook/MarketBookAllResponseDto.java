package com.example.traditionalmarket.dto.response.marketbook;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MarketBookAllResponseDto {
    private UUID marketId;
    private String marketName;
    private boolean visited;
    private LocalDateTime visitedAt;
}
