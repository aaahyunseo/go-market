package com.example.traditionalmarket.dto.response.marketbook;

import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.VisitedMarket;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MarketBookRegionProgressData {

    private List<RegionProgressDto> regions;

    public static MarketBookRegionProgressData of(List<Market> allMarkets, List<VisitedMarket> visitedMarkets) {

        Map<String, List<Market>> regionMarketMap = allMarkets.stream()
                .collect(Collectors.groupingBy(m -> extractRegion(m.getAddress())));

        Map<UUID, LocalDateTime> visitedMap = visitedMarkets.stream()
                .collect(Collectors.toMap(
                        vm -> vm.getMarket().getId(),
                        VisitedMarket::getVisitedAt
                ));

        List<RegionProgressDto> regionProgressList = new ArrayList<>();

        for (String region : regionMarketMap.keySet()) {
            List<Market> regionMarkets = regionMarketMap.get(region);

            long total = regionMarkets.size();
            long visited = regionMarkets.stream().filter(m -> visitedMap.containsKey(m.getId())).count();
            int progressRate = (total == 0) ? 0 : (int) Math.ceil((visited * 100.0) / total);

            regionProgressList.add(RegionProgressDto.builder()
                    .region(region)
                    .visitCount((int) visited)
                    .totalCount((int) total)
                    .progressRate(progressRate)
                    .build());
        }

        regionProgressList.sort((a, b) -> Integer.compare(b.getProgressRate(), a.getProgressRate()));

        return new MarketBookRegionProgressData(regionProgressList);
    }

    private static String extractRegion(String address) {
        if (address == null || address.isEmpty()) return "기타";

        String[] tokens = address.split(" ");
        String rawRegion = tokens[0];

        switch (rawRegion) {
            case "서울특별시": return "서울";
            case "부산광역시":
            case "부산": return "부산";
            case "대구광역시":
            case "대구": return "대구";
            case "인천광역시": return "인천";
            case "광주광역시": return "광주";
            case "대전광역시": return "대전";
            case "울산광역시": return "울산";
            case "세종특별자치시": return "세종";
            case "경기도": return "경기";
            case "강원특별자치도": return "강원";
            case "충청북도": return "충북";
            case "충청남도": return "충남";
            case "전북특별자치도": return "전북";
            case "전라남도":
            case "전남": return "전남";
            case "경상북도":
            case "경북": return "경북";
            case "경상남도": return "경남";
            case "제주특별자치도": return "제주";
            default: return rawRegion;
        }
    }
}

