package com.greenwhitesolutions.servicedesk.ai.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.greenwhitesolutions.servicedesk.ai.provider.AiProvider;

/**
 * Service class that provides AI operations by delegating to the configured AiProvider.
 */
@Service
public class AiService {

    private static final Logger log = LoggerFactory.getLogger(AiService.class);

    private final AiProvider aiProvider;

    /**
     * Creates a new AiService with the given provider.
     *
     * @param aiProvider the AI provider to delegate operations to
     */
    public AiService(AiProvider aiProvider) {
        this.aiProvider = aiProvider;
        log.info("AiService initialized with provider: {}", aiProvider.getClass().getSimpleName());
    }

    /**
     * Classifies the given text and returns a list of labels.
     *
     * @param text the text to classify
     * @return list of classification labels
     */
    public List<String> classify(String text) {
        log.debug("Classifying text of length: {}", text.length());
        return aiProvider.classify(text);
    }

    /**
     * Summarizes the given text.
     *
     * @param text the text to summarize
     * @return a summary of the input text
     */
    public String summarize(String text) {
        log.debug("Summarizing text of length: {}", text.length());
        return aiProvider.summarize(text);
    }
}
