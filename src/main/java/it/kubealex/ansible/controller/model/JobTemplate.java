package it.kubealex.ansible.controller.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobTemplate {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("last_job_failed")
    private Boolean lastRunFailed;

    @JsonProperty("last_job_run")
    private String lastRunTimestamp;

    public Boolean getLastRunFailed() {
        return lastRunFailed;
    }

    public void setLastRunFailed(Boolean lastRunFailed) {
        this.lastRunFailed = lastRunFailed;
    }

    public String getLastRunTimestamp() {
        return lastRunTimestamp;
    }

    public void setLastRunTimestamp(String lastRunTimestamp) {
        this.lastRunTimestamp = lastRunTimestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
