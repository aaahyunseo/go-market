package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.survey.SurveyAnswerDto;
import com.example.traditionalmarket.dto.response.survey.SurveyQuestionResponseData;
import com.example.traditionalmarket.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "점수 기반 AI 시장 추천받기", description = "점수 기반으로 AI의 시장 추천을 받습니다.")
    @PostMapping("/score")
    public ResponseEntity<ResponseDto<String>> getScoreBasedRecommendation(@RequestBody SurveyAnswerDto surveyAnswerDto) {
        String result = surveyService.getScoreBasedRecommendation(surveyAnswerDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "점수 기반 추천 완료", result), HttpStatus.OK);
    }

    @Operation(summary = "RAG 기반 AI 시장 추천받기", description = "RAG 기반으로 AI의 시장 추천을 받습니다.")
    @PostMapping("/rag")
    public ResponseEntity<ResponseDto<String>> getRagBasedRecommendation(@RequestBody SurveyAnswerDto surveyAnswerDto) {
        String result = surveyService.getRagBasedRecommendation(surveyAnswerDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "RAG 기반 추천 완료", result), HttpStatus.OK);
    }
}
