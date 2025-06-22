package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.response.marketbook.MarketBookResponseData;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.MarketBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market-books")
public class MarketBookController {

    private final MarketBookService marketBookService;

    @GetMapping
    public ResponseEntity<ResponseDto<MarketBookResponseData>> getUserInfo(@AuthenticatedUser User user) {
        MarketBookResponseData marketBook = marketBookService.getMarketBook(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "시장도감 조회 완료", marketBook), HttpStatus.OK);
    }

}
