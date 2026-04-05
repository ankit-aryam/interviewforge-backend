package com.interviewforge.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProblemEventConsumer {

    @KafkaListener(topics = "problem-created", groupId = "interviewforge-group")
    public void consume(String message) {

        log.info("Received event: {}", message);

        // simulate processing
        processEvent(message);
    }

    private void processEvent(String message) {

        log.info("Processing event: {}", message);

        // simulate delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Event processed successfully");
    }
}