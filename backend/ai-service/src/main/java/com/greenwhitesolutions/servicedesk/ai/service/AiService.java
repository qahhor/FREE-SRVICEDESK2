package com.greenwhitesolutions.servicedesk.ai.service;

import com.greenwhitesolutions.servicedesk.ai.provider.AiProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that delegates AI operations to the configured provider.
 */
@Service
public class AiService {

    private final AiProvider aiProvider;

    public AiService(AiProvider aiProvider) {
        this.aiProvider = aiProvider;
    }

    /**
     * Classify the given text and return relevant labels.
     *
     * @param text the text to classify
     * @return a list of classification labels
     */
    public List<String> classify(String text) {
        return aiProvider.classify(text);
    }

    /**
     * Summarize the given text.
     *
     * @param text the text to summarize
     * @return a summary of the text
     */
    public String summarize(String text) {
        return aiProvider.summarize(text);
    }
}
