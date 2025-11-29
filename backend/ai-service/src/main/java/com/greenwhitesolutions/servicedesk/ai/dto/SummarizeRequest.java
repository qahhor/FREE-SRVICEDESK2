package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for text summarization endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for text summarization")
public class SummarizeRequest {

    @NotBlank(message = "Text cannot be blank")
    @Schema(description = "Text content to summarize", example = "A long detailed description of an issue...")
    private String text;
}
