package com.greenwhitesolutions.servicedesk.ai.provider;

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
