package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.survey.RecommendRequestDto;
import com.example.traditionalmarket.dto.request.survey.SurveyAnswerDto;
import com.example.traditionalmarket.dto.response.survey.RecommendResponseDto;
import com.example.traditionalmarket.dto.response.survey.SurveyQuestionResponseData;
import com.example.traditionalmarket.entity.Survey;
import com.example.traditionalmarket.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final RestTemplate restTemplate;
    private final SurveyRepository surveyRepository;

    @Value("${ai.api.url}")
    private String aiBaseUrl;

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

    public String getScoreBasedRecommendation(SurveyAnswerDto surveyAnswerDto) {
        String url = aiBaseUrl + "/recommend/score";

        RecommendRequestDto aiRequest = convertToAiRequest(surveyAnswerDto);

        try {
            RecommendResponseDto response = restTemplate.postForObject(url, aiRequest, RecommendResponseDto.class);
            return response.getRecommendedMarket();
        } catch (Exception e) {
            throw new RuntimeException("AI 추천 서버 호출 실패", e);
        }
    }

    public String getRagBasedRecommendation(SurveyAnswerDto surveyAnswerDto) {
        String url = aiBaseUrl + "/recommend/rag";

        RecommendRequestDto aiRequest = convertToAiRequest(surveyAnswerDto);

        try {
            RecommendResponseDto response = restTemplate.postForObject(url, aiRequest, RecommendResponseDto.class);
            return response.getRecommendedMarket();
        } catch (Exception e) {
            throw new RuntimeException("AI RAG 추천 서버 호출 실패", e);
        }
    }

    public RecommendRequestDto convertToAiRequest(SurveyAnswerDto dto) {
        Map<String, String> map = dto.getAnswers();

        return new RecommendRequestDto(
                map.get("1"),  // 지역
                map.get("2"),  // 인원
                map.get("3"),  // 시간
                map.get("4"),  // 구경타입
                map.get("5"),  // 위시리스트
                map.get("6")   // 분위기
        );
    }

}
