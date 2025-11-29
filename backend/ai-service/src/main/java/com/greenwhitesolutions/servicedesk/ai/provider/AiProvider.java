package com.greenwhitesolutions.servicedesk.ai.provider;

import java.util.List;

/**
 * Interface for AI providers that handle text classification and summarization.
 */
public interface AiProvider {

    /**
     * Classify the given text and return relevant labels.
     *
     * @param text the text to classify
     * @return a list of classification labels
     */
    List<String> classify(String text);

    /**
     * Summarize the given text.
     *
     * @param text the text to summarize
     * @return a summary of the text
     */
    String summarize(String text);
}
