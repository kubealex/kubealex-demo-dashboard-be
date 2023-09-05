package it.kubealex.eda.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Event {
    @JsonProperty("name")
    public String eventName;

    public Event() {
    }

    public Event(String eventName) {
        this.eventName = eventName;
    }

}
