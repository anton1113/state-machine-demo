package com.arash.edu.statemachinedemo.controller;

import com.arash.edu.statemachinedemo.dto.ChatbotMessageDTO;
import com.arash.edu.statemachinedemo.dto.ChatbotResponseDTO;
import com.arash.edu.statemachinedemo.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/message")
    public ChatbotResponseDTO sendMessage(@RequestBody ChatbotMessageDTO chatbotMessageDTO) {
        ChatbotResponseDTO responseDTO = new ChatbotResponseDTO();
        responseDTO.setMessage(chatbotService.processMessage(
                chatbotMessageDTO.getMessage() ,chatbotMessageDTO.getSessionId())
        );
        return responseDTO;
    }
}