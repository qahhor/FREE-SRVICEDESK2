package com.greenwhitesolutions.servicedesk.ai.provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of AiProvider for testing and development.
 * Activated when OpenAiProvider is not available (OPENAI_API_KEY is not set or empty).
 */
@Component
@ConditionalOnMissingBean(OpenAiProvider.class)
public class MockAiProvider implements AiProvider {

    @Override
    public List<String> classify(String text) {
        List<String> labels = new ArrayList<>();
        String lowerText = text.toLowerCase();

        // Simple keyword-based classification for mock purposes
        if (lowerText.contains("computer") || lowerText.contains("laptop") || lowerText.contains("pc")) {
            labels.add("hardware");
        }
        if (lowerText.contains("software") || lowerText.contains("application") || lowerText.contains("app")) {
            labels.add("software");
        }
        if (lowerText.contains("network") || lowerText.contains("internet") || lowerText.contains("wifi")) {
            labels.add("network");
        }
        if (lowerText.contains("password") || lowerText.contains("login") || lowerText.contains("access")) {
            labels.add("access");
        }
        if (lowerText.contains("urgent") || lowerText.contains("critical") || lowerText.contains("emergency")) {
            labels.add("urgent");
        }
        if (lowerText.contains("printer") || lowerText.contains("print")) {
            labels.add("printer");
        }
        if (lowerText.contains("email") || lowerText.contains("outlook") || lowerText.contains("mail")) {
            labels.add("email");
        }

        // Default label if no specific keywords found
        if (labels.isEmpty()) {
            labels.add("general");
        }

        return labels;
    }

    @Override
    public String summarize(String text) {
        // Simple mock summarization - return first 100 characters or full text if shorter
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (text.length() <= 100) {
            return text;
        }
        return text.substring(0, 100) + "...";
    }
}
