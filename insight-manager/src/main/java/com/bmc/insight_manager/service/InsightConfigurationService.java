package com.bmc.insight_manager.service;

import com.bmc.insight_manager.entity.InsightConfiguration;

import java.util.List;
import java.util.Optional;

public interface InsightConfigurationService {
    Optional<InsightConfiguration> getConfigurationByName(String name);
    InsightConfiguration createConfiguration(InsightConfiguration config);
    Optional<InsightConfiguration> updateConfiguration(String name, InsightConfiguration updatedConfig);
    boolean deleteConfiguration(String name);
    boolean existsByName(String name);
    List<InsightConfiguration> getAllConfigurations();
}

