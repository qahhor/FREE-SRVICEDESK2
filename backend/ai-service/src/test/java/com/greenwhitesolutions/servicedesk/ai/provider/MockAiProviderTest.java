package com.greenwhitesolutions.servicedesk.ai.provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for MockAiProvider.
 * Tests deterministic behavior of mock responses.
 */
class MockAiProviderTest {

    private MockAiProvider provider;

    @BeforeEach
    void setUp() {
        provider = new MockAiProvider();
    }

    @Test
    @DisplayName("classify returns billing labels for billing-related text")
    void classify_returnsBillingLabels() {
        List<String> labels = provider.classify("I have a question about my billing");

        assertThat(labels).containsExactly("billing", "finance");
    }

    @Test
    @DisplayName("classify returns billing labels for invoice text")
    void classify_returnsLabelsForInvoice() {
        List<String> labels = provider.classify("Where is my invoice?");

        assertThat(labels).containsExactly("billing", "finance");
    }

    @Test
    @DisplayName("classify returns billing labels for payment text")
    void classify_returnsLabelsForPayment() {
        List<String> labels = provider.classify("My payment failed");

        assertThat(labels).containsExactly("billing", "finance");
    }

    @Test
    @DisplayName("classify returns bug labels for bug-related text")
    void classify_returnsBugLabels() {
        List<String> labels = provider.classify("I found a bug in the application");

        assertThat(labels).containsExactly("bug", "technical");
    }

    @Test
    @DisplayName("classify returns bug labels for error text")
    void classify_returnsLabelsForError() {
        List<String> labels = provider.classify("Getting an error message");

        assertThat(labels).containsExactly("bug", "technical");
    }

    @Test
    @DisplayName("classify returns bug labels for crash text")
    void classify_returnsLabelsForCrash() {
        List<String> labels = provider.classify("The application crashes");

        assertThat(labels).containsExactly("bug", "technical");
    }

    @Test
    @DisplayName("classify returns feature-request labels for feature text")
    void classify_returnsFeatureRequestLabels() {
        List<String> labels = provider.classify("Can you add a new feature?");

        assertThat(labels).containsExactly("feature-request", "enhancement");
    }

    @Test
    @DisplayName("classify returns feature-request labels for request text")
    void classify_returnsLabelsForRequest() {
        List<String> labels = provider.classify("I have a request for something new");

        assertThat(labels).containsExactly("feature-request", "enhancement");
    }

    @Test
    @DisplayName("classify returns feature-request labels for enhancement text")
    void classify_returnsLabelsForEnhancement() {
        List<String> labels = provider.classify("This enhancement would be great");

        assertThat(labels).containsExactly("feature-request", "enhancement");
    }

    @Test
    @DisplayName("classify returns default labels for generic text")
    void classify_returnsDefaultLabels() {
        List<String> labels = provider.classify("Hello, I need some help");

        assertThat(labels).containsExactly("general", "support");
    }

    @Test
    @DisplayName("summarize returns mock summary with prefix")
    void summarize_returnsMockSummary() {
        String summary = provider.summarize("This is a test text");

        assertThat(summary).startsWith("Mock summary: ");
        assertThat(summary).contains("This is a test text");
    }

    @Test
    @DisplayName("summarize truncates long text")
    void summarize_truncatesLongText() {
        String longText = "A".repeat(200);
        String summary = provider.summarize(longText);

        assertThat(summary).startsWith("Mock summary: ");
        assertThat(summary).endsWith("...");
        // Mock summary: + 100 chars + ...
        assertThat(summary.length()).isEqualTo("Mock summary: ".length() + 100 + "...".length());
    }

    @Test
    @DisplayName("summarize handles short text without truncation")
    void summarize_handlesShortText() {
        String shortText = "Short";
        String summary = provider.summarize(shortText);

        assertThat(summary).isEqualTo("Mock summary: Short");
    }

    @Test
    @DisplayName("classify is case insensitive")
    void classify_isCaseInsensitive() {
        List<String> labelsLower = provider.classify("billing");
        List<String> labelsUpper = provider.classify("BILLING");
        List<String> labelsMixed = provider.classify("BiLlInG");

        assertThat(labelsLower).isEqualTo(labelsUpper).isEqualTo(labelsMixed);
    }
}
