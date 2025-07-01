package com.example.traditionalmarket.dto.response.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResponseDto {
    @JsonProperty("추천시장")
    private String recommendedMarket;
}
