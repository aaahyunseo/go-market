package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.visitMarket.VisitedMarketDto;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.MarketBook;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.entity.VisitedMarket;
import com.example.traditionalmarket.exception.ConflictException;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.MarketRepository;
import com.example.traditionalmarket.repository.VisitedMarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitedMarketService {

    private final VisitedMarketRepository visitedMarketRepository;
    private final MarketRepository marketRepository;

    public void addVisitedMarket(User user, VisitedMarketDto dto) {
        double userX = Double.parseDouble(dto.getX());
        double userY = Double.parseDouble(dto.getY());

        // 모든 시장을 조회해서 거리 계산
        Market closestMarket = null;
        double minDistance = Double.MAX_VALUE;

        for (Market market : marketRepository.findAll()) {
            String mx = market.getX();
            String my = market.getY();

            if (mx == null || my == null || mx.isBlank() || my.isBlank()) continue;

            double marketX = Double.parseDouble(mx.trim());
            double marketY = Double.parseDouble(my.trim());

            double distance = calculateDistance(userY, userX, marketY, marketX);

            if (distance <= 100 && distance < minDistance) {
                minDistance = distance;
                closestMarket = market;
            }
        }

        // 조건에 맞는 시장이 없다면 예외
        if (closestMarket == null) {
            throw new NotFoundException(ErrorCode.MARKET_LOC_NOT_FOUND);
        }

        MarketBook marketBook = user.getMarketBook();

        // 이미 방문한 시장인지 확인
        if (visitedMarketRepository.existsByMarketBookAndMarket(marketBook, closestMarket)) {
            throw new ConflictException(ErrorCode.DUPLICATED_MARKET);
        }

        // 방문 정보 저장
        VisitedMarket visitedMarket = VisitedMarket.builder()
                .marketBook(marketBook)
                .market(closestMarket)
                .visitedAt(LocalDateTime.now())
                .build();

        visitedMarketRepository.save(visitedMarket);
    }

    // 현재 위치 오차 범위 100m 허용 (Haversine 공식)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

