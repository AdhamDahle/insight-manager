package com.bmc.insight_manager.service;


import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.entity.InsightConfiguration;

public interface MetricsStorageService {
    void storeInsight(Insight insight, InsightConfiguration insightConfiguration);
}

