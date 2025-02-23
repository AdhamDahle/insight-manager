package com.bmc.insight_manager.service.impl;

import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.entity.InsightConfiguration;
import com.bmc.insight_manager.exception.ResourceNotFoundException;
import com.bmc.insight_manager.service.InsightConfigurationService;
import com.bmc.insight_manager.service.InsightService;
import com.bmc.insight_manager.service.MetricsStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of the InsightService that processes incoming Insight messages.
 */
@Service
@RequiredArgsConstructor
public class InsightServiceImpl implements InsightService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsightServiceImpl.class);

    private final InsightConfigurationService insightConfigurationService;
    private final MetricsStorageService metricsStorageService;

    /**
     * Processes an incoming Insight message by fetching its configuration and storing it.
     *
     * @param insight the Insight message to process.
     * @throws ResourceNotFoundException if the configuration for the given insight type is not found.
     */
    @Override
    public void processInsightMessage(Insight insight) {
        LOGGER.info("Processing insight message with type: {}", insight.getType());

        // Get Configuration values for this insight
        String insightType = insight.getType();
        InsightConfiguration insightConfiguration = insightConfigurationService.getConfigurationByName(insightType)
                .orElseThrow(() -> {
                    LOGGER.error("No configuration found for insight type: {}", insightType);
                    return new ResourceNotFoundException("InsightConfiguration", "insightTypeName", insightType);
                });

        LOGGER.info("Found configuration for insight type: {}", insightType);

        // Save insight
        metricsStorageService.storeInsight(insight, insightConfiguration);
        LOGGER.info("Saved insight insight: {}", insight);
    }
}