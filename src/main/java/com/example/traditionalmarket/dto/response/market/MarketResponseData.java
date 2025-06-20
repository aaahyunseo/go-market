package com.example.traditionalmarket.dto.response.market;

import com.example.traditionalmarket.entity.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MarketResponseData {
    List<MarketResponseDto> markets;

    public static MarketResponseData of(List<Market> markets) {
        List<MarketResponseDto> marketResponseDtoList = markets.stream()
                .map(market -> MarketResponseDto.builder()
                        .marketId(market.getId())
                        .marketName(market.getName())
                        .address(market.getAddress())
                        .x(market.getX())
                        .y(market.getY())
                        .build())
                .collect(Collectors.toList());

        return new MarketResponseData(marketResponseDtoList);
    }
}
