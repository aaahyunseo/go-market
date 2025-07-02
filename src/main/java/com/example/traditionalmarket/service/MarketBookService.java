package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.marketbook.MarketBookRequestDto;
import com.example.traditionalmarket.dto.response.marketbook.*;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.MarketBook;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.entity.VisitedMarket;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.MarketRepository;
import com.example.traditionalmarket.repository.VisitedMarketRepository;
import com.example.traditionalmarket.utils.RegionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketBookService {

    private final MarketRepository marketRepository;
    private final VisitedMarketRepository visitedMarketRepository;

    // 전체 시장 도감 조회
    public MarketBookAllResponseData getAllMarketBook(User user) {
        MarketBook marketBook = user.getMarketBook();

        List<Market> allMarkets = marketRepository.findAll();

        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookAllResponseData.of(allMarkets, visitedMarkets);
    }

    // 지역별 시장 도감 조회
    public MarketBookRegionalResponseData getRegionalMarketBook(User user, String region) {
        MarketBook marketBook = user.getMarketBook();

        List<Market> regionalMarkets = marketRepository.findByAddressContaining(region);
        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookRegionalResponseData.of(regionalMarkets, visitedMarkets);
    }

    // 방문한 시장 도감 조회
    public MarketBookResponseData getMarketBook(User user) {
        MarketBook marketBook = user.getMarketBook();

        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookResponseData.of(visitedMarkets);
    }

    // 지역별 시장 도감 진행률 조회
    public MarketBookRegionProgressData getUserRegionProgress(User user) {
        MarketBook marketBook = user.getMarketBook();

        List<Market> allMarkets = marketRepository.findAll();
        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookRegionProgressData.of(allMarkets, visitedMarkets);
    }

    // 방문한 시장 이름 기반 지역명 조회
    public RegionResponseDto getRegionByMarketName(MarketBookRequestDto marketBookRequestDto) {
        Market market = marketRepository.findByName(marketBookRequestDto.getMarketName())
                .orElseThrow(() -> new NotFoundException(ErrorCode.MARKET_NAME_NOT_FOUND));

        String region = RegionUtils.extractRegionFromAddress(market.getAddress());

        return new RegionResponseDto(region);
    }

}