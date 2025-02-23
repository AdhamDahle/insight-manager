package com.bmc.insight_manager.controller;

import com.bmc.insight_manager.entity.InsightConfiguration;
import com.bmc.insight_manager.service.impl.InsightConfigurationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/insight-configurations")
@RequiredArgsConstructor
@Tag(name = "Insight Configurations", description = "Operations related to Insight Configurations")
public class InsightConfigurationController {

    private final InsightConfigurationServiceImpl insightConfigurationService;

    @Operation(summary = "Get all configurations", description = "Retrieve a list of all insight configurations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsightConfiguration.class)))
    })
    @GetMapping
    public ResponseEntity<List<InsightConfiguration>> getAllConfigurations() {
        List<InsightConfiguration> configurations = insightConfigurationService.getAllConfigurations();
        return ResponseEntity.ok(configurations);
    }

    @Operation(summary = "Get configuration by name", description = "Retrieve a specific insight configuration by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the configuration",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsightConfiguration.class))),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content)
    })
    @GetMapping("/{name}")
    public ResponseEntity<InsightConfiguration> getConfigurationByName(@PathVariable String name) {
        Optional<InsightConfiguration> configuration = insightConfigurationService.getConfigurationByName(name);
        return configuration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new configuration", description = "Create a new insight configuration.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsightConfiguration.class)))
    })
    @PostMapping
    public ResponseEntity<InsightConfiguration> createConfiguration(@RequestBody InsightConfiguration config) {
        return ResponseEntity.ok(insightConfigurationService.createConfiguration(config));
    }

    @Operation(summary = "Update configuration", description = "Update an existing insight configuration identified by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configuration updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InsightConfiguration.class))),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content)
    })
    @PutMapping("/{name}")
    public ResponseEntity<InsightConfiguration> updateConfiguration(@PathVariable String name, @RequestBody InsightConfiguration config) {
        Optional<InsightConfiguration> updatedConfig = insightConfigurationService.updateConfiguration(name, config);
        return updatedConfig.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete configuration", description = "Delete an existing insight configuration by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Configuration deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Configuration not found", content = @Content)
    })
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable String name) {
        boolean deleted = insightConfigurationService.deleteConfiguration(name);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
