package com.greenwhitesolutions.servicedesk.ai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for the AI Service.
 * Provides Swagger UI documentation at /swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI aiServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI Service API")
                        .description("AI and LLM integration service for Service Desk Platform. " +
                                "Provides text classification and summarization capabilities.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Green White Solutions")
                                .email("support@greenwhitesolutions.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
