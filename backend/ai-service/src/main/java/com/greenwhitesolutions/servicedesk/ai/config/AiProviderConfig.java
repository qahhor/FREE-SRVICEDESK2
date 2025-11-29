package com.greenwhitesolutions.servicedesk.ai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.greenwhitesolutions.servicedesk.ai.provider.AiProvider;
import com.greenwhitesolutions.servicedesk.ai.provider.MockAiProvider;
import com.greenwhitesolutions.servicedesk.ai.provider.OpenAiProvider;

/**
 * Configuration class for AI provider bean selection.
 * Automatically selects the appropriate provider based on configuration.
 */
@Configuration
public class AiProviderConfig {

    private static final Logger log = LoggerFactory.getLogger(AiProviderConfig.class);

    /**
     * Creates OpenAiProvider bean when:
     * - app.ai.provider is set to 'openai' (or not set, as openai is default)
     * - AND a valid API key is configured
     *
     * @param properties AI configuration properties
     * @return OpenAiProvider instance
     */
    @Bean
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "openai", matchIfMissing = true)
    public AiProvider openAiProvider(AiProperties properties) {
        if (properties.getOpenai().hasApiKey()) {
            log.info("Configuring OpenAI provider with model: {}", properties.getOpenai().getModel());
            return new OpenAiProvider(properties.getOpenai());
        }
        // Fallback to mock if no API key is configured
        log.info("No OpenAI API key configured, falling back to MockAiProvider");
        return new MockAiProvider();
    }

    /**
     * Creates MockAiProvider bean when app.ai.provider is explicitly set to 'mock'.
     *
     * @return MockAiProvider instance
     */
    @Bean
    @ConditionalOnProperty(name = "app.ai.provider", havingValue = "mock")
    public AiProvider mockAiProvider() {
        log.info("Configuring Mock AI provider");
        return new MockAiProvider();
    }
}
