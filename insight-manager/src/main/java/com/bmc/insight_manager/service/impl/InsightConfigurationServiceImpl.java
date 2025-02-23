package com.bmc.insight_manager.service.impl;

import com.bmc.insight_manager.entity.InsightConfiguration;
import com.bmc.insight_manager.repository.InsightConfigurationRepository;
import com.bmc.insight_manager.service.InsightConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsightConfigurationServiceImpl implements InsightConfigurationService {

    private final InsightConfigurationRepository insightConfigurationRepository;

    public Optional<InsightConfiguration> getConfigurationByName(String name) {
        return insightConfigurationRepository.findByInsightTypeName(name);
    }

    public boolean existsByName(String name) {
        return insightConfigurationRepository.existsByInsightTypeName(name);
    }

    @Override
    public List<InsightConfiguration> getAllConfigurations() {
        return insightConfigurationRepository.findAll();
    }

    public InsightConfiguration createConfiguration(InsightConfiguration config) {
        return insightConfigurationRepository.save(config);
    }

    public Optional<InsightConfiguration> updateConfiguration(String name, InsightConfiguration updatedConfig) {
        return insightConfigurationRepository.findByInsightTypeName(name).map(existingConfig -> {
            existingConfig.setInsightConsumed(updatedConfig.getInsightConsumed());
            existingConfig.setTimeSaved(updatedConfig.getTimeSaved());
            return insightConfigurationRepository.save(existingConfig);
        });
    }

    public boolean deleteConfiguration(String name) {
        Optional<InsightConfiguration> config = insightConfigurationRepository.findByInsightTypeName(name);
        if (config.isPresent()) {
            insightConfigurationRepository.delete(config.get());
            return true;
        }
        return false;
    }
}