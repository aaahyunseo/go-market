package com.example.traditionalmarket.dto.response.marketbook;

import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.VisitedMarket;
import com.example.traditionalmarket.utils.RegionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MarketBookAllResponseData {

    private List<MarketBookAllResponseDto> markets;

    public static MarketBookAllResponseData of(List<Market> allMarkets, List<VisitedMarket> visitedMarkets) {
        Map<UUID, LocalDateTime> visitMap = visitedMarkets.stream()
                .collect(Collectors.toMap(
                        vm -> vm.getMarket().getId(),
                        VisitedMarket::getVisitedAt
                ));

        List<MarketBookAllResponseDto> responseList = allMarkets.stream()
                .map(market -> MarketBookAllResponseDto.builder()
                        .marketId(market.getId())
                        .marketName(market.getName())
                        .x(market.getX())
                        .y(market.getY())
                        .region(RegionUtils.extractRegionFromAddress(market.getAddress()))
                        .visited(visitMap.containsKey(market.getId()))
                        .visitedAt(visitMap.get(market.getId()))
                        .build())
                .collect(Collectors.toList());

        return new MarketBookAllResponseData(responseList);
    }
}

