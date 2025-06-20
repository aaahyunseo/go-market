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
@Table(name = "markets")
public class Market extends BaseEntity {
    // 시장 이름
    @Column(nullable = false)
    private String name;

    // 시장 주소
    @Column
    private String address;

    // 시장 x좌표
    @Column
    private String x;

    // 시장 y좌표
    @Column
    private String y;

    // 시장 내 상점
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shop> shops;

    // 방문한 시장
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitedMarket> visitedMarkets;
}
