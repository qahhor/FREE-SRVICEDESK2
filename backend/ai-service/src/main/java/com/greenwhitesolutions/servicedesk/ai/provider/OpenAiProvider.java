package com.greenwhitesolutions.servicedesk.ai.provider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.greenwhitesolutions.servicedesk.ai.config.AiProperties;

/**
 * OpenAI implementation of AiProvider.
 * Uses Spring WebClient to call OpenAI Chat Completions API.
 */
public class OpenAiProvider implements AiProvider {

    private static final Logger log = LoggerFactory.getLogger(OpenAiProvider.class);

    private final WebClient webClient;
    private final AiProperties.OpenAiConfig config;

    /**
     * Creates a new OpenAiProvider with the given configuration.
     *
     * @param config OpenAI configuration
     */
    public OpenAiProvider(AiProperties.OpenAiConfig config) {
        this.config = config;
        this.webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + config.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<String> classify(String text) {
        log.debug("OpenAiProvider.classify called with text length: {}", text.length());

        String prompt = buildClassificationPrompt(text);

        try {
            String response = callChatCompletion(prompt);
            return parseLabels(response);
        } catch (Exception e) {
            log.error("Error calling OpenAI for classification", e);
            // Return empty list on error - caller can handle fallback
            return new ArrayList<>();
        }
    }

    @Override
    public String summarize(String text) {
        log.debug("OpenAiProvider.summarize called with text length: {}", text.length());

        String prompt = buildSummarizationPrompt(text);

        try {
            return callChatCompletion(prompt);
        } catch (Exception e) {
            log.error("Error calling OpenAI for summarization", e);
            // Return empty string on error - caller can handle fallback
            return "";
        }
    }

    /**
     * Calls OpenAI Chat Completions API with the given prompt.
     *
     * @param prompt the user prompt
     * @return the assistant's response content
     */
    private String callChatCompletion(String prompt) {
        Map<String, Object> requestBody = Map.of(
                "model", config.getModel(),
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 500,
                "temperature", 0.3
        );

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = webClient.post()
                    .uri("/v1/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(config.getTimeout()))
                    .block();

            if (response == null) {
                log.warn("Received null response from OpenAI");
                return "";
            }

            return extractContent(response);
        } catch (WebClientResponseException e) {
            log.error("OpenAI API error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("OpenAI API error: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the content from OpenAI response.
     */
    @SuppressWarnings("unchecked")
    private String extractContent(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                if (message != null) {
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            log.error("Error parsing OpenAI response", e);
        }
        return "";
    }

    /**
     * Builds the prompt for text classification.
     */
    private String buildClassificationPrompt(String text) {
        return """
                Classify the following support ticket text into categories.
                Return ONLY a comma-separated list of labels, nothing else.
                Possible labels: billing, bug, feature-request, technical, general, support, urgent, security.
                
                Text to classify:
                %s
                
                Labels:""".formatted(text);
    }

    /**
     * Builds the prompt for text summarization.
     */
    private String buildSummarizationPrompt(String text) {
        return """
                Summarize the following support ticket text in one or two sentences.
                Be concise and capture the main issue or request.
                
                Text to summarize:
                %s
                
                Summary:""".formatted(text);
    }

    /**
     * Parses the comma-separated labels from the response.
     */
    private List<String> parseLabels(String response) {
        List<String> labels = new ArrayList<>();
        if (response == null || response.isBlank()) {
            return labels;
        }

        String[] parts = response.trim().split("[,\\s]+");
        for (String part : parts) {
            String label = part.trim().toLowerCase();
            if (!label.isEmpty()) {
                labels.add(label);
            }
        }

        return labels;
    }
}
