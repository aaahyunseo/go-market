package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.chat.ChatQuestDto;
import com.example.traditionalmarket.dto.response.Chat.ChatAnswerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${ai.api.url}")
    private String aiBaseUrl;

    public ChatAnswerDto getChatAnswer(ChatQuestDto chatQuestDto) {
        String url = aiBaseUrl + "/rag-chat";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ChatQuestDto> request = new HttpEntity<>(chatQuestDto, headers);

            String responseString = restTemplate.postForObject(url, request, String.class);

            return objectMapper.readValue(responseString, ChatAnswerDto.class);

        } catch (Exception e) {
            throw new RuntimeException("AI 챗봇 서버 호출 실패", e);
        }
    }
}