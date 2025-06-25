package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.response.survey.SurveyQuestionResponseData;
import com.example.traditionalmarket.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @Operation(summary = "설문 질문 리스트 조회", description = "추천 코스 설문을 위한 질문 리스트가 조회됩니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<SurveyQuestionResponseData>> getQuestions() {
        SurveyQuestionResponseData surveyQuestions = surveyService.getQuestions();
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "추천 코스 설문 질문 리스트 조회 완료", surveyQuestions), HttpStatus.OK);
    }

}
