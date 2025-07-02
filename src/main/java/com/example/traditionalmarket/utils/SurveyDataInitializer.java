package com.example.traditionalmarket.utils;

import com.example.traditionalmarket.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurveyDataInitializer implements CommandLineRunner {

    private final SurveyService surveyService;

    @Override
    public void run(String... args) {
        surveyService.initSurveyData();
    }
}
