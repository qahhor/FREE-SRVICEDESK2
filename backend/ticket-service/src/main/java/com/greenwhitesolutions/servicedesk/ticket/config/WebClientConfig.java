package com.greenwhitesolutions.servicedesk.ticket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for WebClient used to communicate with ai-service.
 */
@Configuration
public class WebClientConfig {

    @Value("${app.ai-service.url}")
    private String aiServiceUrl;

    @Bean
    public WebClient aiServiceWebClient() {
        return WebClient.builder()
                .baseUrl(aiServiceUrl)
                .build();
    }
}
