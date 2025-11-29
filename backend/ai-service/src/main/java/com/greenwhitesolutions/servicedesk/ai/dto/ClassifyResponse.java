package com.greenwhitesolutions.servicedesk.ai.dto;

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
