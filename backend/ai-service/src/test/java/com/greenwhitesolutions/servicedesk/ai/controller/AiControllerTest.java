package com.greenwhitesolutions.servicedesk.ai.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.greenwhitesolutions.servicedesk.ai.service.AiService;

/**
 * Unit tests for AiController using MockMvc.
 */
@WebMvcTest(AiController.class)
class AiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AiService aiService;

    @Test
    @DisplayName("POST /api/v1/ai/classify returns labels")
    void classify_returnsLabels() throws Exception {
        when(aiService.classify(anyString())).thenReturn(Arrays.asList("billing", "support"));

        mockMvc.perform(post("/api/v1/ai/classify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"I have a billing question\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.labels").isArray())
                .andExpect(jsonPath("$.labels[0]").value("billing"))
                .andExpect(jsonPath("$.labels[1]").value("support"));
    }

    @Test
    @DisplayName("POST /api/v1/ai/classify returns empty array when no labels")
    void classify_returnsEmptyArray() throws Exception {
        when(aiService.classify(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/v1/ai/classify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"Some text\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.labels").isArray())
                .andExpect(jsonPath("$.labels").isEmpty());
    }

    @Test
    @DisplayName("POST /api/v1/ai/classify returns 400 for blank text")
    void classify_returnsBadRequestForBlankText() throws Exception {
        mockMvc.perform(post("/api/v1/ai/classify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/ai/classify returns 400 for missing text")
    void classify_returnsBadRequestForMissingText() throws Exception {
        mockMvc.perform(post("/api/v1/ai/classify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/ai/summarize returns summary")
    void summarize_returnsSummary() throws Exception {
        when(aiService.summarize(anyString())).thenReturn("This is a summary.");

        mockMvc.perform(post("/api/v1/ai/summarize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"A long detailed description of an issue\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value("This is a summary."));
    }

    @Test
    @DisplayName("POST /api/v1/ai/summarize returns 400 for blank text")
    void summarize_returnsBadRequestForBlankText() throws Exception {
        mockMvc.perform(post("/api/v1/ai/summarize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/ai/summarize returns 400 for missing text")
    void summarize_returnsBadRequestForMissingText() throws Exception {
        mockMvc.perform(post("/api/v1/ai/summarize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
