package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "market_books")
public class MarketBook extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 방문한 시장 정보
    @OneToMany(mappedBy = "marketBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitedMarket> visitedMarkets;
}
