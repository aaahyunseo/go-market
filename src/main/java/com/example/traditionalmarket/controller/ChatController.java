package com.example.traditionalmarket.controller;

import com.example.traditionalmarket.dto.ResponseDto;
import com.example.traditionalmarket.dto.request.chat.ChatQuestDto;
import com.example.traditionalmarket.dto.response.Chat.ChatAnswerDto;
import com.example.traditionalmarket.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "RAG 기반 시장 챗봇", description = "전통시장 관련 질문에 대해 AI 챗봇이 답변합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<ChatAnswerDto>> getChatAnswer(@RequestBody @Valid ChatQuestDto chatQuestDto) {
        ChatAnswerDto answer = chatService.getChatAnswer(chatQuestDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "AI 챗봇 답변 성공", answer), HttpStatus.CREATED);
    }
}
