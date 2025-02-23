package com.bmc.insight_manager.repository;


import com.bmc.insight_manager.entity.InsightConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsightConfigurationRepository extends JpaRepository<InsightConfiguration, Long> {

    // Find by Insight Type Name (since it's unique)
    Optional<InsightConfiguration> findByInsightTypeName(String insightTypeName);

    // Check if an Insight Type Exists
    boolean existsByInsightTypeName(String insightTypeName);
}
