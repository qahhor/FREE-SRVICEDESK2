package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for text classification.
 */
@Schema(description = "Request for classifying text")
public record ClassifyRequest(
        @Schema(description = "The text to classify", example = "My computer won't start and the screen is black")
        String text
) {}
