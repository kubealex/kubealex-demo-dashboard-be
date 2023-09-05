package it.kubealex.eda.webhook.model;

import it.kubealex.eda.event.model.Event;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class WebhookEvent extends Event {
    @JsonProperty("target_hosts")
    public String eventTargetHosts;

    public WebhookEvent() {
    }

    public WebhookEvent(String eventName, String eventMessage, String eventTargetHosts) {
        this.eventMessage = eventMessage;
        this.eventName = eventName;
        this.eventTargetHosts = eventTargetHosts;
    }

}
