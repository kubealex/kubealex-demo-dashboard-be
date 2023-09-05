package it.kubealex.eda.mqtt.service;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import it.kubealex.eda.mqtt.model.MqttEvent;
import jakarta.inject.Inject;

public class MqttEventSender {
    @Inject
    @Channel("mqtt-topic")
    Emitter<MqttEvent> mqttEventEmitter;

    public MqttEvent sendMqttEvent() {
        MqttEvent mqttEvent = new MqttEvent("greeting", "Hello from Quarkus", "Quarkus App");
        mqttEventEmitter.send(mqttEvent);
        return mqttEvent;
    }
}
