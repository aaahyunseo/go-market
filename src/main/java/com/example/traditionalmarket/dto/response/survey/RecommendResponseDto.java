package com.example.traditionalmarket.dto.response.survey;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResponseDto {
    @JsonProperty("추천시장")
    private String recommendedMarket;
    private String x;
    private String y;
    private String region;
}
