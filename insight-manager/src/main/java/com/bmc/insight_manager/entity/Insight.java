package com.bmc.insight_manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entity representing an Insight")
public class Insight {

    @JsonProperty("type")
    @NotEmpty(message = "Type can not be empty")
    @Schema(description = "Type of the insight", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @NotEmpty(message = "tenantId can not be empty")
    @JsonProperty("tenantId")
    @Schema(description = "Tenant identifier", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantId;

    @NotEmpty(message = "createdAt can not be empty")
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Timestamp when the insight was created", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-10-12T08:30:00")
    private LocalDateTime createdAt;
}
