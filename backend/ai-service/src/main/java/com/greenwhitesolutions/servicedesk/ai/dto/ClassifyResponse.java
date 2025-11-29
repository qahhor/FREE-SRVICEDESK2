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
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for text classification endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response body containing classification labels")
public class ClassifyResponse {

    @Schema(description = "List of classification labels", example = "[\"billing\", \"bug\"]")
    private List<String> labels;
}
