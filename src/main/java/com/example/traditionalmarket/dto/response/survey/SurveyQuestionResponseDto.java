package com.example.traditionalmarket.dto.response.survey;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SurveyQuestionResponseDto {
    private UUID surveyId;
    private int num;
    private String question;
}
