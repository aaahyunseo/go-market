package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.response.market.MarketResponseData;
import com.example.traditionalmarket.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/markets")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "전체 전통시장 리스트 조회", description = "모든 지역에 대한 전통시장 목록이 조회됩니다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<MarketResponseData>> getAllMarkets() {
        MarketResponseData markets = marketService.getAllMarkets();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "전체 전통시장 리스트 조회 완료", markets), HttpStatus.OK);
    }

    @Operation(summary = "지역별 전통시장 리스트 조회", description = "유저가 선택한 지역별 전통시장 목록이 조회됩니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<MarketResponseData>> getMarketsByRegion(@RequestParam("region") String region) {
        MarketResponseData markets = marketService.getRegionalMarkets(region);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, region + " 전통시장 리스트 조회 완료", markets), HttpStatus.OK);
    }
}
