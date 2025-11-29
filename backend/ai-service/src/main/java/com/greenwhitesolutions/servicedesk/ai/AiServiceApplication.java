package com.greenwhitesolutions.servicedesk.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.greenwhitesolutions.servicedesk.ai.config.AiProperties;

/**
 * Main application class for the AI Service.
 * Provides AI-powered text classification and summarization endpoints.
 */
@SpringBootApplication
@EnableConfigurationProperties(AiProperties.class)
public class AiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiServiceApplication.class, args);
    }
}
