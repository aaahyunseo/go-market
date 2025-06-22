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
        String marketName = dto.getMarketName();

        Market market = marketRepository.findByName(marketName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARKET_NAME_NOT_FOUND));

        double marketX = Double.parseDouble(market.getX().trim());
        double marketY = Double.parseDouble(market.getY().trim());

        double distance = calculateDistance(userY, userX, marketY, marketX);

        if (distance <= 100) {
            MarketBook marketBook = user.getMarketBook();

            if (visitedMarketRepository.existsByMarketBookAndMarket(marketBook, market)) {
                throw new ConflictException(ErrorCode.DUPLICATED_MARKET);
            }

            VisitedMarket visitedMarket = VisitedMarket.builder()
                    .marketBook(marketBook)
                    .market(market)
                    .visitedAt(LocalDateTime.now())
                    .build();

            visitedMarketRepository.save(visitedMarket);
        } else {
            throw new NotFoundException(ErrorCode.MARKET_LOC_NOT_FOUND);
        }

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

