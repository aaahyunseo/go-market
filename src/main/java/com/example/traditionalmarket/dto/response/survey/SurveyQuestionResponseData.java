package com.example.traditionalmarket.dto.response.survey;

import com.example.traditionalmarket.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SurveyQuestionResponseData {
    private List<SurveyQuestionResponseDto> questions;

    public static SurveyQuestionResponseData from(List<Survey> surveys) {
        List<SurveyQuestionResponseDto> dtoList = surveys.stream()
                .map(survey -> SurveyQuestionResponseDto.builder()
                        .surveyId(survey.getId())
                        .num(survey.getNum())
                        .question(survey.getQuestion())
                        .build())
                .collect(Collectors.toList());

        return new SurveyQuestionResponseData(dtoList);
    }
}
