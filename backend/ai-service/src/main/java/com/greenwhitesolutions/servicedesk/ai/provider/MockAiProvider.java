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
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock implementation of AiProvider for testing and local development.
 * Returns deterministic responses without calling any external API.
 * Used when no API key is configured.
 */
public class MockAiProvider implements AiProvider {

    private static final Logger log = LoggerFactory.getLogger(MockAiProvider.class);

    /**
     * Default labels returned for classification.
     */
    private static final List<String> DEFAULT_LABELS = Arrays.asList("general", "support");

    /**
     * Default summary prefix.
     */
    private static final String SUMMARY_PREFIX = "Mock summary: ";

    @Override
    public List<String> classify(String text) {
        log.debug("MockAiProvider.classify called with text length: {}", text.length());

        // Provide deterministic mock labels based on text content
        if (text.toLowerCase().contains("billing") || text.toLowerCase().contains("invoice") 
                || text.toLowerCase().contains("payment")) {
            return Arrays.asList("billing", "finance");
        }
        if (text.toLowerCase().contains("bug") || text.toLowerCase().contains("error") 
                || text.toLowerCase().contains("crash")) {
            return Arrays.asList("bug", "technical");
        }
        if (text.toLowerCase().contains("feature") || text.toLowerCase().contains("request") 
                || text.toLowerCase().contains("enhancement")) {
            return Arrays.asList("feature-request", "enhancement");
        }

        return DEFAULT_LABELS;
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
        log.debug("MockAiProvider.summarize called with text length: {}", text.length());

        // Return a deterministic mock summary
        int maxLength = Math.min(text.length(), 100);
        String truncated = text.substring(0, maxLength);
        if (text.length() > 100) {
            truncated += "...";
        }
        return SUMMARY_PREFIX + truncated;
    }
}
