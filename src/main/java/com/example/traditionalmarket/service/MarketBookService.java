package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.response.marketbook.MarketBookAllResponseData;
import com.example.traditionalmarket.dto.response.marketbook.MarketBookResponseData;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.MarketBook;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.entity.VisitedMarket;
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

    public MarketBookAllResponseData getAllMarketBook(User user) {
        MarketBook marketBook = user.getMarketBook();

        List<Market> allMarkets = marketRepository.findAll();

        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookAllResponseData.of(allMarkets, visitedMarkets);
    }

    public MarketBookResponseData getMarketBook(User user) {
        MarketBook marketBook = user.getMarketBook();

        List<VisitedMarket> visitedMarkets = visitedMarketRepository.findByMarketBook(marketBook);

        return MarketBookResponseData.of(visitedMarkets);
    }
}

