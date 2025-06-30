package com.example.traditionalmarket.dto.response.marketbook;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionProgressDto {
    private String region;
    private int visitCount;
    private int totalCount;
    private int progressRate;
}
