package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.response.marketbook.MarketBookAllResponseData;
import com.example.traditionalmarket.dto.response.marketbook.MarketBookResponseData;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.MarketBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market-books")
public class MarketBookController {

    private final MarketBookService marketBookService;

    @GetMapping("/all")
    public ResponseEntity<ResponseDto<MarketBookAllResponseData>> getAllMarketBook(@AuthenticatedUser User user) {
        MarketBookAllResponseData marketBook = marketBookService.getAllMarketBook(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "전체 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<MarketBookAllResponseData>> getRegionalMarketBook(@AuthenticatedUser User user, @RequestParam("region") String region) {
        MarketBookAllResponseData marketBook = marketBookService.getRegionalMarketBook(user, region);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, region + " 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

    @GetMapping("/visited")
    public ResponseEntity<ResponseDto<MarketBookResponseData>> getMarketBook(@AuthenticatedUser User user) {
        MarketBookResponseData marketBook = marketBookService.getMarketBook(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "방문 완료한 시장도감 조회 완료", marketBook), HttpStatus.OK);
    }
}
