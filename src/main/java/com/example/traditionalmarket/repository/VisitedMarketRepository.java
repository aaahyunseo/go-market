package com.example.traditionalmarket.repository;

import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.MarketBook;
import com.example.traditionalmarket.entity.VisitedMarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VisitedMarketRepository extends JpaRepository<VisitedMarket, UUID> {
    List<VisitedMarket> findByMarketBook(MarketBook marketBook);
    boolean existsByMarketBookAndMarket(MarketBook marketBook, Market market);
    Long countByMarketBook(MarketBook marketBook);
}
