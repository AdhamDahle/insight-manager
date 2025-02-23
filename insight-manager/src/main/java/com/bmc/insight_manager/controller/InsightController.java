package com.bmc.insight_manager.controller;

import com.bmc.insight_manager.entity.Insight;
import com.bmc.insight_manager.service.InsightProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insight")
public class InsightController {

    @Autowired
    InsightProducerService insightProducerService;

    @PostMapping
    public String createInsight(@RequestBody Insight insight){
        return insightProducerService.createInsight(insight);
    }
}
