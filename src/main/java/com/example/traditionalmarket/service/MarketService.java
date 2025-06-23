package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.response.market.MarketResponseData;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.repository.MarketRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    // 시장 데이터 초기 저장
    public void importFromJsonFile() {
        if (marketRepository.count() > 0) {
            System.out.println("✅ 시장 데이터가 이미 존재합니다.");
            return;
        }

        try {
            File file = new File("src/main/resources/data/전국전통시장표준데이터.json");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(file);
            JsonNode records = root.path("records");

            int count = 0;

            for (JsonNode item : records) {
                String name = item.path("시장명").asText();
                String address = item.path("소재지도로명주소").asText();
                String x = item.path("경도").asText();
                String y = item.path("위도").asText();

                Market market = Market.builder()
                        .name(name)
                        .address(address)
                        .x(x)
                        .y(y)
                        .build();

                marketRepository.save(market);
                count++;
            }

            System.out.println("✅ 전국 전통시장 " + count + "개 저장 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 전통 시장 리스트 조회하기
    public MarketResponseData getAllMarkets() {
        List<Market> markets = marketRepository.findAll();
        return MarketResponseData.of(markets);
    }
}
