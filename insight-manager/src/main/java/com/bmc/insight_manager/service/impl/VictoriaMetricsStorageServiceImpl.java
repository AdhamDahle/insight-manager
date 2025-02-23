package com.bmc.insight_manager.service.impl;

import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.entity.InsightConfiguration;
import com.bmc.insight_manager.service.MetricsStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

/**
 * Implementation of the MetricsStorageService that stores insights in VictoriaMetrics.
 */
@Service
@RequiredArgsConstructor
public class VictoriaMetricsStorageServiceImpl implements MetricsStorageService {

    private static final Logger logger = LoggerFactory.getLogger(VictoriaMetricsStorageServiceImpl.class);

    private final RestTemplate restTemplate;

    @Value("${victoriametrics.url}")
    private String VM_URL;

    /**
     * Stores an insight in VictoriaMetrics.
     *
     * @param insight              The insight to be stored.
     * @param insightConfiguration The corresponding insight configuration.
     * @throws HttpClientErrorException   If there is a client-side error (4xx status codes).
     * @throws HttpServerErrorException   If there is a server-side error (5xx status codes).
     * @throws ResourceAccessException    If there is a connectivity issue.
     */
    @Override
    public void storeInsight(Insight insight, InsightConfiguration insightConfiguration) {
        try {
            // Ensure proper headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "text/plain");

            // Convert insight to payload
            String payload = formatInsightForVictoriaMetrics(insight, insightConfiguration);
            HttpEntity<String> request = new HttpEntity<>(payload, headers);

            // Send request to VictoriaMetrics
            ResponseEntity<String> response = restTemplate.exchange(VM_URL, HttpMethod.POST, request, String.class);
            logger.info("Successfully stored insight metrics: {}", payload);
        } catch (HttpClientErrorException e) {
            logger.error("Client error while storing insight in VictoriaMetrics: {}", e.getMessage(), e);
            throw e;
        } catch (HttpServerErrorException e) {
            logger.error("Server error while storing insight in VictoriaMetrics: {}", e.getMessage(), e);
            throw e;
        } catch (ResourceAccessException e) {
            logger.error("Network error while accessing VictoriaMetrics: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while storing insight in VictoriaMetrics: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Formats the insight into the expected VictoriaMetrics payload format.
     *
     * @param insight              The insight data.
     * @param insightConfiguration The corresponding insight configuration.
     * @return The formatted payload as a string.
     */
    private String formatInsightForVictoriaMetrics(Insight insight, InsightConfiguration insightConfiguration) {
        long timestamp = Instant.now().getEpochSecond(); // Get current time in seconds

        return String.format(
                "insight_consumed_metric{type=\"%s\",tenantId=\"%s\"} %d %d\n" +
                        "time_saved_metric{type=\"%s\",tenantId=\"%s\"} %d %d",
                insight.getType(), insight.getTenantId(), insightConfiguration.getInsightConsumed(), timestamp,
                insight.getType(), insight.getTenantId(), insightConfiguration.getTimeSaved(), timestamp
        );
    }
}
