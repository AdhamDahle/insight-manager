package com.bmc.insight_manager.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicManager {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicManager.class);

    @Value("${kafka.topic.insight-created}")
    private String topic;

    /**
     * Bean to ensure Kafka topic exists at startup.
     *
     * @return NewTopic instance representing the topic.
     */
    @Bean
    public NewTopic createTopicIfNotExists() {
        return TopicBuilder.name(topic)
                .partitions(3)
                .build();
    }
}