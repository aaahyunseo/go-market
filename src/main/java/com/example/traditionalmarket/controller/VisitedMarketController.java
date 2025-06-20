package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.visitMarket.VisitedMarketDto;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.VisitedMarketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/visited-markets")
public class VisitedMarketController {
    private final VisitedMarketService visitedMarketService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> addVisitedMarket(@AuthenticatedUser User user, @RequestBody @Valid VisitedMarketDto dto) {
        visitedMarketService.addVisitedMarket(user, dto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "방문한 시장 등록 완료"), HttpStatus.CREATED);
    }

}
