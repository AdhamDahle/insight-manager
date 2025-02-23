package com.bmc.insight_manager.kafka.listener;

import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.exception.ResourceNotFoundException;
import com.bmc.insight_manager.service.InsightService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaInsightListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaInsightListener.class);

    private final ObjectMapper objectMapper;
    private final InsightService insightService;

    @Value("${kafka.topic.insight-created}")
    private String topic;

    @KafkaListener(topics = "#{'${kafka.topic.insight-created}'}", groupId = "#{'${kafka.group.id}'}")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            logger.info("Received value in Kafka listener from topic {}: {}", topic, record.value());
            Insight insight = objectMapper.readValue(record.value(), Insight.class);
            logger.info("Received Insight: {}", insight);

            // Process the insight message
            insightService.processInsightMessage(insight);

        } catch (ResourceNotFoundException ex) {
            logger.error("Resource Not Found: {}. Skipping message.", ex.getMessage(), ex);
        } catch (JsonProcessingException ex) {
            logger.error("Failed to parse JSON message: {}", record.value(), ex);
        } catch (Exception ex) {
            logger.error("Unhandled exception in Kafka listener: {}", ex.getMessage(), ex);
            throw ex;  // Let Kafka retry or send to DLT
        }
    }

}
