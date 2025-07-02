package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.survey.RecommendRequestDto;
import com.example.traditionalmarket.dto.request.survey.SurveyAnswerDto;
import com.example.traditionalmarket.dto.response.survey.RecommendResponseDto;
import com.example.traditionalmarket.dto.response.survey.SurveyQuestionResponseData;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.Survey;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.MarketRepository;
import com.example.traditionalmarket.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SurveyService {

    private final RestTemplate restTemplate;
    private final SurveyRepository surveyRepository;
    private final MarketRepository marketRepository;

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

    // 점수 기반 AI 시장 추천 받기
    public RecommendResponseDto getScoreBasedRecommendation(SurveyAnswerDto surveyAnswerDto) {
        String url = aiBaseUrl + "/recommend/score";
        RecommendRequestDto aiRequest = convertToAiRequest(surveyAnswerDto);
        String region = surveyAnswerDto.getAnswers().get("1");

        return getRecommendationFromAi(url, aiRequest, region);
    }

    // RAG 기반 AI 시장 추천 받기
    public RecommendResponseDto getRagBasedRecommendation(SurveyAnswerDto surveyAnswerDto) {
        String url = aiBaseUrl + "/recommend/rag";
        RecommendRequestDto aiRequest = convertToAiRequest(surveyAnswerDto);
        String region = surveyAnswerDto.getAnswers().get("1");

        return getRecommendationFromAi(url, aiRequest, region);
    }

    // AI 서버로 요청 및 응답 처리
    public RecommendResponseDto getRecommendationFromAi(String url, RecommendRequestDto aiRequest, String region) {

        try {
            RecommendResponseDto aiResponse = restTemplate.postForObject(url, aiRequest, RecommendResponseDto.class);

            String marketName = aiResponse.getRecommendedMarket().trim();
            log.info("[AI 추천 시장 이름] : {}", marketName);

            if ("추천할 수 있는 시장이 없습니다".equals(marketName)) {
                throw new NotFoundException(ErrorCode.RECOMMEND_NOT_FOUND);
            }

            // 같은 이름 가진 시장들 모두 조회
            List<Market> candidates = marketRepository.findAllByName(marketName);

            if (candidates.isEmpty()) {
                throw new NotFoundException(ErrorCode.MARKET_NAME_NOT_FOUND);
            }

            // 요청된 지역에 포함되는 시장만 필터링
            List<Market> filteredByRegion = candidates.stream()
                    .filter(m -> m.getAddress() != null && m.getAddress().contains(region))
                    .toList();

            Market selectedMarket;

            if (!filteredByRegion.isEmpty()) {
                // 같은 이름 중 지역 일치하는 거 있으면 그걸 선택
                selectedMarket = filteredByRegion.get(0);
            } else {
                // 그래도 없으면 일단 첫번째 후보라도 반환 (안그러면 계속 에러 터짐)
                selectedMarket = candidates.get(0);
            }

            return RecommendResponseDto.builder()
                    .recommendedMarket(selectedMarket.getName())
                    .x(selectedMarket.getX())
                    .y(selectedMarket.getY())
                    .region(region)
                    .build();

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("AI 추천 서버 호출 실패", e);
        }
    }

    // 프론트에서 받은 request와 AI로 전달할 request 매핑
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
