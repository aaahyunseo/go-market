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

        String region = extractRegionFromAddress(market.getAddress());

        return new RegionResponseDto(region);
    }

    private String extractRegionFromAddress(String address) {
        if (address == null) return "기타";
        if (address.startsWith("서울")) return "서울";
        if (address.startsWith("경기")) return "경기";
        if (address.startsWith("부산")) return "부산";
        if (address.startsWith("대구")) return "대구";
        if (address.startsWith("광주")) return "광주";
        if (address.startsWith("대전")) return "대전";
        if (address.startsWith("울산")) return "울산";
        if (address.startsWith("세종")) return "세종";
        if (address.startsWith("인천")) return "인천";
        if (address.startsWith("강원")) return "강원";
        if (address.startsWith("충북")) return "충청북도";
        if (address.startsWith("충남")) return "충청남도";
        if (address.startsWith("전북")) return "전라북도";
        if (address.startsWith("전남")) return "전라남도";
        if (address.startsWith("경북")) return "경상북도";
        if (address.startsWith("경남")) return "경상남도";
        if (address.startsWith("제주")) return "제주";
        return "기타";
    }

}