package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "visited_markets")
public class VisitedMarket extends BaseEntity {

    // 시장 도감
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_book_id")
    private MarketBook marketBook;

    // 방문한 시장 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    // 방문한 날짜
    @Column(nullable = false)
    private LocalDateTime visitedAt;
}

