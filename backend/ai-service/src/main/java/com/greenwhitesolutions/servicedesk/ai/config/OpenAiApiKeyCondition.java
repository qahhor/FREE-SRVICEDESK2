package com.greenwhitesolutions.servicedesk.ai.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition that checks if the OpenAI API key is configured and not empty.
 */
public class OpenAiApiKeyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String apiKey = context.getEnvironment().getProperty("app.ai.openai.api-key");
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}
