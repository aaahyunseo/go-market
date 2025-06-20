package com.example.traditionalmarket.dto.response.marketbook;

import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.VisitedMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MarketBookResponseData {

    private List<MarketBookResponseDto> markets;

    public static MarketBookResponseData of(List<Market> allMarkets, List<VisitedMarket> visitedMarkets) {
        Map<UUID, LocalDateTime> visitMap = visitedMarkets.stream()
                .collect(Collectors.toMap(
                        vm -> vm.getMarket().getId(),
                        VisitedMarket::getVisitedAt
                ));

        List<MarketBookResponseDto> responseList = allMarkets.stream()
                .map(market -> MarketBookResponseDto.builder()
                        .marketId(market.getId())
                        .marketName(market.getName())
                        .visited(visitMap.containsKey(market.getId()))
                        .visitedAt(visitMap.get(market.getId()))
                        .build())
                .collect(Collectors.toList());

        return new MarketBookResponseData(responseList);
    }
}

