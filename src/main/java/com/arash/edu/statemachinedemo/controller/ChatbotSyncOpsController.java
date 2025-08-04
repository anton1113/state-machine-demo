package com.arash.edu.statemachinedemo.controller;

import com.arash.edu.statemachinedemo.dto.ChatbotMessageDTO;
import com.arash.edu.statemachinedemo.dto.ChatbotResponseDTO;
import com.arash.edu.statemachinedemo.service.ChatbotSyncOpsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatbot")
public class ChatbotSyncOpsController {

    private final ChatbotSyncOpsService chatbotService;

    @PostMapping("/message/sync")
    public ChatbotResponseDTO sendMessageSync(@RequestBody ChatbotMessageDTO chatbotMessageDTO) {
        String response = chatbotService.processMessage(
                chatbotMessageDTO.getMessage(),
                chatbotMessageDTO.getSessionId()
        );
        ChatbotResponseDTO responseDTO = new ChatbotResponseDTO();
        responseDTO.setMessage(response);
        return responseDTO;
    }
}