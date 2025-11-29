package com.greenwhitesolutions.servicedesk.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for text classification endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for text classification")
public class ClassifyRequest {

    @NotBlank(message = "Text cannot be blank")
    @Schema(description = "Text content to classify", example = "I need help with my billing issue")
    private String text;
}
