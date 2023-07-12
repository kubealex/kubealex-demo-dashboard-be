package it.kubealex.eda.alertmanager.service;

import java.util.List;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import it.kubealex.eda.alertmanager.model.Alert;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1/alerts")
@RegisterRestClient
public interface AlertService {
    @POST
    Response sendAlert(List<Alert> alert);
}
