package com.example.traditionalmarket.dto.request.survey;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class SurveyAnswerDto {
    private Map<String, String> answers = new HashMap<>();

    @JsonAnySetter
    public void addAnswer(String key, String value) {
        answers.put(key, value);
    }
}

