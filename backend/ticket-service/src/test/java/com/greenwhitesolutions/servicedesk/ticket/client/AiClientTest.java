package com.greenwhitesolutions.servicedesk.ticket.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Unit tests for AiClient using MockWebServer.
 */
class AiClientTest {

    private static MockWebServer mockServer;
    private AiClient aiClient;
    private ObjectMapper objectMapper;

    @BeforeAll
    static void startServer() throws Exception {
        mockServer = new MockWebServer();
        mockServer.start();
    }

    @AfterAll
    static void shutdownServer() throws Exception {
        mockServer.shutdown();
    }

    @BeforeEach
    void setUp() {
        String baseUrl = String.format("http://localhost:%s", mockServer.getPort());
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        aiClient = new AiClient(webClient);
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("classify returns labels from AI service response")
    void classify_returnsLabels() throws JsonProcessingException {
        // Given
        String responseJson = objectMapper.writeValueAsString(new ClassifyResponse(List.of("billing", "support")));
        mockServer.enqueue(new MockResponse()
                .setBody(responseJson)
                .addHeader("Content-Type", "application/json"));

        // When
        List<String> labels = aiClient.classify("I have a billing question");

        // Then
        assertThat(labels).containsExactly("billing", "support");
    }

    @Test
    @DisplayName("classify returns empty list when AI service is unavailable")
    void classify_returnsEmptyListOnError() {
        // Given
        mockServer.enqueue(new MockResponse().setResponseCode(500));

        // When
        List<String> labels = aiClient.classify("Some text");

        // Then
        assertThat(labels).isEmpty();
    }

    @Test
    @DisplayName("classify returns empty list when AI service returns empty labels")
    void classify_returnsEmptyListFromService() throws JsonProcessingException {
        // Given
        String responseJson = objectMapper.writeValueAsString(new ClassifyResponse(List.of()));
        mockServer.enqueue(new MockResponse()
                .setBody(responseJson)
                .addHeader("Content-Type", "application/json"));

        // When
        List<String> labels = aiClient.classify("Some text");

        // Then
        assertThat(labels).isEmpty();
    }

    // Helper record to serialize response
    private record ClassifyResponse(List<String> labels) {}
}
