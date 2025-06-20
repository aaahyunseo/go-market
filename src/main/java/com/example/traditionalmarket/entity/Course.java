package com.example.traditionalmarket.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "courses")
public class Course extends BaseEntity{
    // AI 추천 코스 설명 저장
    @Column(nullable = false)
    private String description;

    // 코스를 추천 받은 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
