package com.example.traditionalmarket.dto.response.market;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MarketResponseDto {
    private UUID marketId;
    private String marketName;
    private String address;
    private String x;
    private String y;
}
