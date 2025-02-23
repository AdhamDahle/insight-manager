package com.adham.dummy_assistant.kafka;

import com.adham.dummy_assistant.entity.Insight;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, Insight> kafkaTemplate;


    public void sendMessage(Insight insight) {

        String productKey = UUID.randomUUID().toString();

        kafkaTemplate.send(
                "insight_created_topic_9",
                productKey,
                insight)
        ;
        LOGGER.info("Message Sent " + insight);
    }
}

