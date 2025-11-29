package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request DTO for text summarization.
 */
@Schema(description = "Request for summarizing text")
public record SummarizeRequest(
        @Schema(description = "The text to summarize", example = "This is a long description of a technical issue...")
        String text
) {}
