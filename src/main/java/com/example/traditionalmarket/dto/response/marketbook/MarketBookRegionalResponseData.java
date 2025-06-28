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
public class MarketBookRegionalResponseData {

    private int visitCount;
    private int totalCount;
    private int progressRate;
    private List<MarketBookAllResponseDto> markets;

    public static MarketBookRegionalResponseData of(List<Market> regionalMarkets, List<VisitedMarket> visitedMarkets) {

        List<UUID> regionalMarketIds = regionalMarkets.stream()
                .map(Market::getId)
                .collect(Collectors.toList());

        List<VisitedMarket> regionalVisitedMarkets = visitedMarkets.stream()
                .filter(vm -> regionalMarketIds.contains(vm.getMarket().getId()))
                .collect(Collectors.toList());

        int visitCount = regionalVisitedMarkets.size();
        int totalCount = regionalMarkets.size();

        int progressRate = (totalCount == 0) ? 0
                : (int) Math.ceil((visitCount * 100.0) / totalCount);

        Map<UUID, LocalDateTime> visitMap = visitedMarkets.stream()
                .collect(Collectors.toMap(
                        vm -> vm.getMarket().getId(),
                        VisitedMarket::getVisitedAt
                ));

        List<MarketBookAllResponseDto> responseList = regionalMarkets.stream()
                .map(market -> MarketBookAllResponseDto.builder()
                        .marketId(market.getId())
                        .marketName(market.getName())
                        .x(market.getX())
                        .y(market.getY())
                        .visited(visitMap.containsKey(market.getId()))
                        .visitedAt(visitMap.get(market.getId()))
                        .build())
                .collect(Collectors.toList());

        return new MarketBookRegionalResponseData(visitCount, totalCount, progressRate, responseList);
    }
}
