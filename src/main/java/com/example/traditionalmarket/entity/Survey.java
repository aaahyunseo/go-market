package com.example.traditionalmarket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "surveys")
public class Survey extends BaseEntity{
    // 설문 번호
    @Column(nullable = false)
    private int num;

    // 설문 질문
    @Column(nullable = false)
    private String question;
}
