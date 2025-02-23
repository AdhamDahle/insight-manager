package com.adham.dummy_assistant.controller;


import com.adham.dummy_assistant.entity.Insight;
import com.adham.dummy_assistant.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insight")
public class InsightController {

    @Autowired
    private KafkaProducer kafkaProducer;


    @PostMapping("publish")
    public ResponseEntity<String> publish(@RequestBody Insight insight) {
        kafkaProducer.sendMessage(insight);
        return ResponseEntity.ok("Message sent to insight topic");
    }
}
