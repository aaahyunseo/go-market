package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.response.survey.SurveyQuestionResponseData;
import com.example.traditionalmarket.entity.Survey;
import com.example.traditionalmarket.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    // 설문 질문 DB에 저장하기
    public void initSurveyData() {
        if (surveyRepository.count() > 0) {
            System.out.println("✅ 이미 설문 데이터가 존재합니다.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("data/surveyQuestions.csv"),
                StandardCharsets.UTF_8))) {

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(",", 2);
                if (tokens.length >= 2) {
                    Survey survey = Survey.builder()
                            .num(Integer.parseInt(tokens[0].trim()))
                            .question(tokens[1].trim())
                            .build();
                    surveyRepository.save(survey);
                }
            }

            System.out.println("✅ 설문 질문 초기 데이터 저장 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 설문 내용 가져오기
    public SurveyQuestionResponseData getQuestions() {
        List<Survey> surveys = surveyRepository.findAll();
        return SurveyQuestionResponseData.from(surveys);
    }

    // 설문 답변 받기
    public void getAnswers() {

    }
}
