package com.interviewforge.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemEventProducer {

    public static final String TOPIC_PROBLEM_CREATED = "problem-created";

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Publishes asynchronously; failures are logged and do not roll back DB (fire-and-forget).
     */
    public void sendProblemCreatedEvent(String message) {
        kafkaTemplate
                .send(TOPIC_PROBLEM_CREATED, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka send failed topic={} payload={}", TOPIC_PROBLEM_CREATED, message, ex);
                    } else if (result != null && result.getRecordMetadata() != null) {
                        var meta = result.getRecordMetadata();
                        log.debug(
                                "Kafka send ok topic={} partition={} offset={}",
                                meta.topic(),
                                meta.partition(),
                                meta.offset());
                    }
                });
    }
}
