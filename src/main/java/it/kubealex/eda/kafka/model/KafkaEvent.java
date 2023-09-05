package it.kubealex.eda.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import it.kubealex.eda.event.model.Event;

@RegisterForReflection
public class KafkaEvent extends Event {

    @JsonProperty("eventMessage")
    public String eventMessage;

    public KafkaEvent() {
    }

    public KafkaEvent(String eventName, String eventMessage) {
        this.eventMessage = eventMessage;
        this.eventName = eventName;
    }

}
