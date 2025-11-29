package com.greenwhitesolutions.servicedesk.ai.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Configuration properties for AI service.
 * Mapped to app.ai.* in application.yml.
 */
@ConfigurationProperties(prefix = "app.ai")
@Getter
@Setter
public class AiProperties {

    /**
     * AI provider to use (openai, mock).
     * Defaults to mock if no API key is configured.
     */
    private String provider = "openai";

    /**
     * OpenAI specific configuration.
     */
    private OpenAiConfig openai = new OpenAiConfig();

    @Getter
    @Setter
    public static class OpenAiConfig {
        /**
         * OpenAI API key. If empty or not set, MockAiProvider will be used.
         */
        private String apiKey = "";

        /**
         * Model to use for AI operations.
         */
        private String model = "gpt-4o";

        /**
         * Request timeout in seconds.
         */
        private int timeout = 30;

        /**
         * Base URL for OpenAI API.
         */
        private String baseUrl = "https://api.openai.com";

        /**
         * Checks if a valid API key is configured.
         *
         * @return true if API key is set and not empty
         */
        public boolean hasApiKey() {
            return apiKey != null && !apiKey.isBlank();
        }
    }
}
