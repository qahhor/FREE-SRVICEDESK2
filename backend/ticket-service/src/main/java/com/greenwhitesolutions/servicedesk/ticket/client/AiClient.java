package com.greenwhitesolutions.servicedesk.ticket.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Client for communicating with the AI service.
 */
@Component
public class AiClient {

    private final WebClient webClient;

    public AiClient(WebClient aiServiceWebClient) {
        this.webClient = aiServiceWebClient;
    }

    /**
     * Classify the given text using the AI service.
     *
     * @param text the text to classify
     * @return a list of classification labels
     */
    public List<String> classify(String text) {
        try {
            ClassifyResponse response = webClient.post()
                    .uri("/api/v1/ai/classify")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("text", text))
                    .retrieve()
                    .bodyToMono(ClassifyResponse.class)
                    .block();

            return response != null ? response.labels() : Collections.emptyList();
        } catch (Exception e) {
            // Return empty list if AI service is unavailable
            return Collections.emptyList();
        }
    }

    /**
     * Response record for classification endpoint.
     */
    private record ClassifyResponse(List<String> labels) {}
}
