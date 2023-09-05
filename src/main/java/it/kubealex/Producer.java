package it.kubealex;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import it.kubealex.ansible.controller.model.JobTemplate;
import it.kubealex.ansible.controller.service.AAPControllerService;
import it.kubealex.eda.alertmanager.model.Alert;
import it.kubealex.eda.alertmanager.service.AlertService;
import it.kubealex.eda.event.model.Event;
import it.kubealex.eda.kafka.service.KafkaEventSender;
import it.kubealex.eda.mqtt.service.MqttEventSender;
import it.kubealex.eda.webhook.model.WebhookEvent;
import it.kubealex.eda.webhook.service.WebhookService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.annotations.Broadcast;

@Path("/")
public class Producer {

    @RestClient
    WebhookService webhookService;
    @RestClient
    AlertService alertService;
    @RestClient
    AAPControllerService aapControllerService;

    @Inject
    KafkaEventSender kafkaEventSender;

    @Inject
    MqttEventSender mqttEventSender;

    @GET
    @Path("/kafka/")
    @Produces(MediaType.APPLICATION_JSON)

    public Event sendKafkaEvent() {
        return kafkaEventSender.sendKafkaEvent();
    }

    @GET
    @Path("/mqtt/")
    @Produces(MediaType.APPLICATION_JSON)
    public Event sendMqttEvent() {
        return mqttEventSender.sendMqttEvent();
    }

    @GET
    @Path("/webhook/")
    @Produces(MediaType.APPLICATION_JSON)
    public WebhookEvent sendWebhookEvent() {
        WebhookEvent testEvent = new WebhookEvent("greeting", "Hello from Quarkus", null);
        webhookService.sendEvent(testEvent);
        return testEvent;
    }

    @GET
    @Path("/alertmanager/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alert> sendAlert() {
        List<Alert> alertList = new ArrayList<>();
        Alert testAlert = new Alert("quarkusAlert", "Quarkus fired this alert", "localhost");
        alertList.add(testAlert);
        alertService.sendAlert(alertList);
        return alertList;
    }

    /*
     * @GET
     *
     * @Path("/ansible/")
     *
     * @Produces(MediaType.APPLICATION_JSON)
     * public List<JobTemplate> getJobTemplates() throws JsonMappingException,
     * JsonProcessingException {
     * Response response = aapControllerService.getJobTemplates(1);
     * JsonArray resultsArray =
     * response.readEntity(JsonObject.class).getJsonArray("results");
     * List<JobTemplate> jobTemplates = new
     * ObjectMapper().readValue(resultsArray.toString(),
     * new TypeReference<List<JobTemplate>>() {
     * });
     *
     * return jobTemplates;
     * }
     */

    @GET
    @Path("/ansible/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<JobTemplate> getJobTemplates() throws JsonMappingException,
            JsonProcessingException {
        return fetchAllPages(aapControllerService);
    }

    private List<JobTemplate> fetchAllPages(AAPControllerService service)
            throws JsonMappingException, JsonProcessingException {
        List<JobTemplate> jobTemplates = new ArrayList<>();
        Boolean hasNextPage = true;
        Integer page = 1;
        while (hasNextPage) {
            Response response = service.getJobTemplates(page);
            JsonObject responseObject = response.readEntity(JsonObject.class);
            JsonArray resultsArray = responseObject.getJsonArray("results");
            List<JobTemplate> pageResults = new ObjectMapper().readValue(resultsArray.toString(),
                    new TypeReference<List<JobTemplate>>() {
                    });
            jobTemplates.addAll(pageResults);
            hasNextPage = responseObject.isNull("next") ? false : true;
            page++;
        }
        return jobTemplates;
    }

    @GET
    @Path("/ansible/launch/{id}")
    public JsonObject launchTemplateInfo(@RestPath Integer id) {
        Response response = aapControllerService.launchTemplateInfo(id);
        JsonObject json = response.readEntity(JsonObject.class);
        return json;
    }

    @POST
    @Path("/ansible/launch/{id}")
    public String launchTemplate(@RestPath Integer id) {
        Response response = aapControllerService.launchTemplate(id);
        String json = response.readEntity(String.class);
        return json;
    }
}
