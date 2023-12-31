package it.kubealex.ansible.controller.service;

import java.util.Base64;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/api/v2")
@ClientHeaderParam(name = "Authorization", value = "{genBasicAuth}")
@RegisterRestClient(configKey = "controller-api")

public interface AAPControllerService {

    String controllerUsername = ConfigProvider.getConfig().getValue("controller.username", String.class);
    String controllerPassword = ConfigProvider.getConfig().getValue("controller.password", String.class);

    @GET
    @Path("/job_templates/")
    @ClientHeaderParam(name = "Authorization", value = "{genBasicAuth}")
    // public Response getJobTemplates(@RestQuery("page_size") Integer pageSize);
    public Response getJobTemplates(@RestQuery("page") Integer page);

    default String genBasicAuth() {
        return "Basic " +
                Base64.getEncoder().encodeToString((controllerUsername + ":" + controllerPassword).getBytes());
    }

    @GET
    @Path("/job_templates/{id}/launch/")
    @ClientHeaderParam(name = "Authorization", value = "{genBasicAuth}")
    public Response launchTemplateInfo(@RestPath Integer id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/job_templates/{id}/launch/")
    @ClientHeaderParam(name = "Authorization", value = "{genBasicAuth}")
    public Response launchTemplate(@RestPath Integer id);

}
