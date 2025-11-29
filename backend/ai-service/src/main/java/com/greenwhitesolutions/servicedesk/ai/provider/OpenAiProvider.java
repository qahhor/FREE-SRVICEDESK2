package com.greenwhitesolutions.servicedesk.ai.provider;

import com.greenwhitesolutions.servicedesk.ai.config.OpenAiApiKeyCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * OpenAI implementation of AiProvider.
 * Activated when OPENAI_API_KEY environment variable is set and not empty.
 * Note: This is a placeholder implementation. Full OpenAI integration will be added later.
 */
@Component
@Conditional(OpenAiApiKeyCondition.class)
public class OpenAiProvider implements AiProvider {

    @Value("${app.ai.openai.api-key}")
    private String apiKey;

    @Value("${app.ai.openai.model:gpt-4o}")
    private String model;

    @Override
    public List<String> classify(String text) {
        // Placeholder: In production, this would call OpenAI API
        // For now, returns basic classification
        return Arrays.asList("ai-classified", "needs-review");
    }

    @Override
    public String summarize(String text) {
        // Placeholder: In production, this would call OpenAI API
        // For now, returns truncated text
        if (text == null || text.isEmpty()) {
            return "";
        }
        if (text.length() <= 150) {
            return text;
        }
        return text.substring(0, 150) + "...";
    }
}
