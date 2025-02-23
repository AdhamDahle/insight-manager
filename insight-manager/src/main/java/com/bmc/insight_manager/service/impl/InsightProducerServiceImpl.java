package com.bmc.insight_manager.service.impl;

import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.service.InsightProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsightProducerServiceImpl implements InsightProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsightProducerServiceImpl.class);

    private final KafkaTemplate<String, Insight> kafkaTemplate;
    @Value("${kafka.topic.insight-created}")
    private String topic;

    @Override
    public String createInsight(Insight insight) {
        String eventID = UUID.randomUUID().toString();

        try{
            kafkaTemplate.send(topic, eventID, insight);
            LOGGER.info("Insight was created {}", insight);
        }catch (Exception ex){
            LOGGER.error("Something went wrong while pushing insigh to kafka queue", ex);
        }

        return eventID;
    }
}
