package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.authentication.AuthenticatedUser;
import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.visitMarket.VisitedMarketDto;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.service.VisitedMarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visited-markets")
public class VisitedMarketController {
    private final VisitedMarketService visitedMarketService;

    @Operation(summary = "방문한 시장 등록", description = "GPS의 x,y좌표를 시장의 위치와 비교해 방문한 시장을 등록합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> addVisitedMarket(@Parameter(hidden = true) @AuthenticatedUser User user, @RequestBody @Valid VisitedMarketDto dto) {
        String marketName = visitedMarketService.addVisitedMarket(user, dto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, marketName + " 등록 완료"), HttpStatus.CREATED);
    }

}
