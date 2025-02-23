package com.bmc.insight_manager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Exception thrown when a requested resource is not found.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceType;
    private final String identifierName;
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