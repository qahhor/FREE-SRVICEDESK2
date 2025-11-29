package com.greenwhitesolutions.servicedesk.ai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenwhitesolutions.servicedesk.ai.dto.ClassifyRequest;
import com.greenwhitesolutions.servicedesk.ai.dto.ClassifyResponse;
import com.greenwhitesolutions.servicedesk.ai.dto.SummarizeRequest;
import com.greenwhitesolutions.servicedesk.ai.dto.SummarizeResponse;
import com.greenwhitesolutions.servicedesk.ai.service.AiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for AI operations.
 * Provides endpoints for text classification and summarization.
 */
@RestController
@RequestMapping("/api/v1/ai")
@Validated
@Tag(name = "AI Operations", description = "Endpoints for AI-powered text classification and summarization")
public class AiController {

    private static final Logger log = LoggerFactory.getLogger(AiController.class);

    private final AiService aiService;

    /**
     * Creates a new AiController with the given service.
     *
     * @param aiService the AI service to handle operations
     */
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /**
     * Classifies the given text and returns classification labels.
     *
     * @param request the classification request containing text
     * @return response with classification labels
     */
    @PostMapping("/classify")
    @Operation(summary = "Classify text", description = "Classifies the provided text and returns a list of labels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully classified the text"),
            @ApiResponse(responseCode = "400", description = "Invalid request - text is blank or missing")
    })
    public ResponseEntity<ClassifyResponse> classify(@Valid @RequestBody ClassifyRequest request) {
        log.info("Received classification request for text of length: {}", request.getText().length());

        var labels = aiService.classify(request.getText());
        return ResponseEntity.ok(new ClassifyResponse(labels));
    }

    /**
     * Summarizes the given text.
     *
     * @param request the summarization request containing text
     * @return response with the summary
     */
    @PostMapping("/summarize")
    @Operation(summary = "Summarize text", description = "Summarizes the provided text and returns a concise summary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully summarized the text"),
            @ApiResponse(responseCode = "400", description = "Invalid request - text is blank or missing")
    })
    public ResponseEntity<SummarizeResponse> summarize(@Valid @RequestBody SummarizeRequest request) {
        log.info("Received summarization request for text of length: {}", request.getText().length());

        var summary = aiService.summarize(request.getText());
        return ResponseEntity.ok(new SummarizeResponse(summary));
    }
}
