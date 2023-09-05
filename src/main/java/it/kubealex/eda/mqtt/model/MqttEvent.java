package it.kubealex.eda.mqtt.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import it.kubealex.eda.event.model.Event;

@RegisterForReflection
public class MqttEvent extends Event {

    @JsonProperty("eventBody")
    public String eventMessage;
    @JsonProperty("eventSource")
    public String eventSource;

    public MqttEvent() {
    }

    public MqttEvent(String eventName, String eventMessage, String eventSource) {
        this.eventMessage = eventMessage;
        this.eventName = eventName;
        this.eventSource = eventSource;
    }

}
