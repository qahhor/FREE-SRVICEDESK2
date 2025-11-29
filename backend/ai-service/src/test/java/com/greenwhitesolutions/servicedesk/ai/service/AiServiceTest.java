package com.greenwhitesolutions.servicedesk.ai.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.greenwhitesolutions.servicedesk.ai.provider.AiProvider;

/**
 * Unit tests for AiService.
 */
@ExtendWith(MockitoExtension.class)
class AiServiceTest {

    @Mock
    private AiProvider aiProvider;

    private AiService aiService;

    @BeforeEach
    void setUp() {
        aiService = new AiService(aiProvider);
    }

    @Test
    @DisplayName("classify delegates to provider and returns labels")
    void classify_delegatesToProvider() {
        String text = "I have a billing question";
        List<String> expectedLabels = Arrays.asList("billing", "finance");
        when(aiProvider.classify(text)).thenReturn(expectedLabels);

        List<String> labels = aiService.classify(text);

        assertThat(labels).isEqualTo(expectedLabels);
        verify(aiProvider).classify(text);
    }

    @Test
    @DisplayName("classify returns empty list when provider returns empty")
    void classify_returnsEmptyList() {
        String text = "Some text";
        when(aiProvider.classify(text)).thenReturn(Collections.emptyList());

        List<String> labels = aiService.classify(text);

        assertThat(labels).isEmpty();
        verify(aiProvider).classify(text);
    }

    @Test
    @DisplayName("summarize delegates to provider and returns summary")
    void summarize_delegatesToProvider() {
        String text = "A long description of an issue that needs summarizing.";
        String expectedSummary = "Issue needs summarizing.";
        when(aiProvider.summarize(text)).thenReturn(expectedSummary);

        String summary = aiService.summarize(text);

        assertThat(summary).isEqualTo(expectedSummary);
        verify(aiProvider).summarize(text);
    }

    @Test
    @DisplayName("summarize returns empty string when provider returns empty")
    void summarize_returnsEmptyString() {
        String text = "Some text";
        when(aiProvider.summarize(text)).thenReturn("");

        String summary = aiService.summarize(text);

        assertThat(summary).isEmpty();
        verify(aiProvider).summarize(text);
    }
}
