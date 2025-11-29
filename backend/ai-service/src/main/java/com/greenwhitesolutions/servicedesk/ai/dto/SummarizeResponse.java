package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for text summarization.
 */
@Schema(description = "Response containing summarized text")
public record SummarizeResponse(
        @Schema(description = "The summarized text", example = "Computer not starting, black screen issue")
        String summary
) {}
