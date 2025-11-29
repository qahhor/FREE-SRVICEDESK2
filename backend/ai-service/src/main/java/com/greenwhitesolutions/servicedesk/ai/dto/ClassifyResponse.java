package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response DTO for text classification.
 */
@Schema(description = "Response containing classification labels")
public record ClassifyResponse(
        @Schema(description = "List of classification labels", example = "[\"hardware\", \"critical\", \"desktop\"]")
        List<String> labels
) {}
