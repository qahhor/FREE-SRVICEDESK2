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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for text summarization endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response body containing text summary")
public class SummarizeResponse {

    @Schema(description = "Summary of the input text", example = "Customer reported a billing discrepancy.")
    private String summary;
}
