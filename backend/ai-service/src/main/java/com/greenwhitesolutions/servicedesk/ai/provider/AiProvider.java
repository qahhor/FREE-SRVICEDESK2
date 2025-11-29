package com.greenwhitesolutions.servicedesk.ai.provider;

import java.util.List;

/**
 * Interface for AI provider implementations.
 * Provides pluggable AI functionality for classification and summarization.
 */
public interface AiProvider {

    /**
     * Classifies the given text and returns a list of labels.
     *
     * @param text the text to classify
     * @return list of classification labels (e.g., ["billing", "bug"])
     */
    List<String> classify(String text);

    /**
     * Summarizes the given text.
     *
     * @param text the text to summarize
     * @return a summary of the input text
     */
    String summarize(String text);
}
