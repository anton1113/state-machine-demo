package com.arash.edu.statemachinedemo.service.ai;

import com.arash.edu.statemachinedemo.domain.AiStructuredOutput;
import com.arash.edu.statemachinedemo.domain.AiSystemMessage;
import com.arash.edu.statemachinedemo.dto.ai.CarSearchFiltersStructuredOutput;
import com.arash.edu.statemachinedemo.repository.AiStructuredOutputRepository;
import com.arash.edu.statemachinedemo.repository.AiSystemMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterExtractionService {

    private static final String SYSTEM_MESSAGE_KEY = "filter_extraction";

    private final ChatClient chatClient;
    private final AiSystemMessageRepository aiSystemMessageRepository;
    private final AiStructuredOutputRepository aiStructuredOutputRepository;

    public CarSearchFiltersStructuredOutput extractFilters(String userMessage) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(OpenAiApi.ChatModel.GPT_4_1_MINI)
                .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, getStructuredOutputSchema()))
                .build();
        return chatClient.prompt()
                .user(userMessage)
                .system(getSystemMessage())
                .options(options)
                .call()
                .entity(CarSearchFiltersStructuredOutput.class);
    }

    private String getSystemMessage() {
        return aiSystemMessageRepository.findByKey(SYSTEM_MESSAGE_KEY)
                .map(AiSystemMessage::getMessage)
                .orElseThrow(() -> new RuntimeException("System message not found for key:" + SYSTEM_MESSAGE_KEY));
    }

    private String getStructuredOutputSchema() {
        return aiStructuredOutputRepository.findByKey(SYSTEM_MESSAGE_KEY)
                .map(AiStructuredOutput::getJsonSchema)
                .orElseThrow(() -> new RuntimeException("Structured output schema not found for key: " + SYSTEM_MESSAGE_KEY));
    }
}
