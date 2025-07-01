package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.marketbook.MarketBookRequestDto;
import com.example.traditionalmarket.dto.response.marketbook.*;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.MarketBookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market-books")
public class MarketBookController {

    private final MarketBookService marketBookService;

    @Operation(summary = "전체 시장도감 조회", description = "모든 지역에 대한 유저의 전체 시장도감 목록이 조회됩니다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<MarketBookAllResponseData>> getAllMarketBook(@AuthenticatedUser User user) {
        MarketBookAllResponseData marketBook = marketBookService.getAllMarketBook(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "전체 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

    @Operation(summary = "지역별 시장도감 조회", description = "유저가 선택한 지역별 시장도감 목록이 조회됩니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<MarketBookRegionalResponseData>> getRegionalMarketBook(@AuthenticatedUser User user, @RequestParam("region") String region) {
        MarketBookRegionalResponseData marketBook = marketBookService.getRegionalMarketBook(user, region);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, region + " 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

    @Operation(summary = "방문한 시장도감 조회", description = "유저가 방문한 시장도감 목록이 조회됩니다.")
    @GetMapping("/visited")
    public ResponseEntity<ResponseDto<MarketBookResponseData>> getMarketBook(@AuthenticatedUser User user) {
        MarketBookResponseData marketBook = marketBookService.getMarketBook(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "방문 완료한 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

    @Operation(summary = "모든 지역 시장도감 진행률 조회", description = "유저의 모든 지역 시장도감 진행률 목록이 조회됩니다.")
    @GetMapping("/mypage")
    public ResponseEntity<ResponseDto<MarketBookRegionProgressData>> getRegionProgress(@AuthenticatedUser User user) {
        MarketBookRegionProgressData marketBookRegionProgressData = marketBookService.getUserRegionProgress(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "모든 지역 시장도감 진행률 조회 완료", marketBookRegionProgressData), HttpStatus.OK);
    }

    @Operation(summary = "방문 시장이름 기반 지역명 조회", description = "클릭한 시장 이름을 보내면 해당 시장의 지역명이 조회됩니다.")
    @PostMapping("/region-by-market")
    public ResponseEntity<ResponseDto<RegionResponseDto>> getRegionalMarketBookByMarketName(@Valid @RequestBody MarketBookRequestDto marketBookRequestDto) {
        RegionResponseDto region = marketBookService.getRegionByMarketName(marketBookRequestDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, marketBookRequestDto.getMarketName() + " 지역명 조회 완료", region), HttpStatus.OK);
    }
}
