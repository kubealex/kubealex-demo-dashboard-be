package it.kubealex.eda.kafka.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import it.kubealex.eda.kafka.model.KafkaEvent;
import jakarta.inject.Inject;

public class KafkaEventSender {
    @Inject
    @Channel("eda-topic")
    Emitter<KafkaEvent> kafkaEventEmitter;

    public KafkaEvent sendKafkaEvent() {
        KafkaEvent testEvent = new KafkaEvent("greeting", "Hello from Quarkus");
        kafkaEventEmitter.send(testEvent);
        return testEvent;
    }
}
