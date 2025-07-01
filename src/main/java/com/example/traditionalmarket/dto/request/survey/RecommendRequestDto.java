package com.example.traditionalmarket.dto.request.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendRequestDto {
    private String 지역;
    private String 인원;
    private String 시간;
    private String 구경타입;
    private String 위시리스트;
    private String 분위기;
}
