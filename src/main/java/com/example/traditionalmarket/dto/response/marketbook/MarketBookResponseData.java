package com.example.traditionalmarket.dto.response.marketbook;

import com.example.traditionalmarket.entity.VisitedMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
public class MarketBookResponseData {
    private List<MarketBookResponseDto> markets;

    public static MarketBookResponseData of(List<VisitedMarket> visitedMarkets) {
        List<MarketBookResponseDto> dtoList = visitedMarkets.stream()
                .map(vm -> MarketBookResponseDto.builder()
                        .marketId(vm.getMarket().getId())
                        .marketName(vm.getMarket().getName())
                        .visitedAt(vm.getVisitedAt())
                        .build())
                .collect(Collectors.toList());

        return new MarketBookResponseData(dtoList);
    }

}
