package com.greenwhitesolutions.servicedesk.ai.provider;

import java.util.List;

/**
 * Interface for AI providers that handle text classification and summarization.
 * Interface for AI provider implementations.
 * Provides pluggable AI functionality for classification and summarization.
 */
public interface AiProvider {

    /**
     * Classify the given text and return relevant labels.
     *
     * @param text the text to classify
     * @return a list of classification labels
     * Classifies the given text and returns a list of labels.
     *
     * @param text the text to classify
     * @return list of classification labels (e.g., ["billing", "bug"])
     */
    List<String> classify(String text);

    /**
     * Summarize the given text.
     *
     * @param text the text to summarize
     * @return a summary of the text
     * Summarizes the given text.
     *
     * @param text the text to summarize
     * @return a summary of the input text
     */
    String summarize(String text);
}
