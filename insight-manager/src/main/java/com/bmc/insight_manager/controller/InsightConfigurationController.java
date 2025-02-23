package com.bmc.insight_manager.controller;

import com.bmc.insight_manager.entity.InsightConfiguration;
import com.bmc.insight_manager.service.impl.InsightConfigurationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insight-configurations")
@RequiredArgsConstructor
public class InsightConfigurationController {

    private final InsightConfigurationServiceImpl insightConfigurationService;

    // Get all Insight Configurations
    @GetMapping
    public ResponseEntity<List<InsightConfiguration>> getAllConfigurations() {
        List<InsightConfiguration> configurations = insightConfigurationService.getAllConfigurations();
        return ResponseEntity.ok(configurations);
    }

    // Get Insight Configuration by Name
    @GetMapping("/{name}")
    public ResponseEntity<InsightConfiguration> getConfigurationByName(@PathVariable String name) {
        Optional<InsightConfiguration> configuration = insightConfigurationService.getConfigurationByName(name);
        return configuration.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new Insight Configuration
    @PostMapping
    public ResponseEntity<InsightConfiguration> createConfiguration(@RequestBody InsightConfiguration config) {
        return ResponseEntity.ok(insightConfigurationService.createConfiguration(config));
    }

    // Update an existing Insight Configuration
    @PutMapping("/{name}")
    public ResponseEntity<InsightConfiguration> updateConfiguration(@PathVariable String name, @RequestBody InsightConfiguration config) {
        Optional<InsightConfiguration> updatedConfig = insightConfigurationService.updateConfiguration(name, config);
        return updatedConfig.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an Insight Configuration
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable String name) {
        boolean deleted = insightConfigurationService.deleteConfiguration(name);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
