package com.bmc.insight_manager.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Exception thrown when a requested resource is not found.
 */
@Getter
@Schema(name = "ResourceNotFoundException", description = "Exception thrown when a requested resource is not found.")
public class ResourceNotFoundException extends RuntimeException {

    @Schema(description = "Type of the resource that was not found", example = "User")
    private final String resourceType;

    @Schema(description = "Name of the identifier used to look up the resource", example = "id")
    private final String identifierName;

    @Schema(description = "Value of the identifier", example = "12345")
    private final String identifierValue;

    public ResourceNotFoundException(String resourceType, String identifierName, String identifierValue) {
        super(String.format("%s not found where %s = '%s'",
                resourceType,
                identifierName,
                identifierValue)
        );
        this.resourceType = resourceType;
        this.identifierName = identifierName;
        this.identifierValue = identifierValue;
    }
}
