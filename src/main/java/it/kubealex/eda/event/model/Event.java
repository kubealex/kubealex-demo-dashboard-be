package it.kubealex.eda.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Event {
    @JsonProperty("eventName")
    public String eventName;
    @JsonProperty("eventMessage")
    public String eventMessage;

    public Event() {
    }

    public Event(String eventName, String eventMessage) {
        this.eventMessage = eventMessage;
        this.eventName = eventName;

    }

}
