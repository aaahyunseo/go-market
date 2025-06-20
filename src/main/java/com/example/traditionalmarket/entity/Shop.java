package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "shops")
public class Shop extends BaseEntity {
    // 상점 이름
    @Column(nullable = false)
    private String name;

    // 상점 주소
    @Column
    private String address;

    // 상점 x좌표
    @Column
    private String x;

    // 상점 y좌표
    @Column
    private String y;

    // 상점 관련 영상 url
    @Column
    private String url;

    // 시장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;
}
