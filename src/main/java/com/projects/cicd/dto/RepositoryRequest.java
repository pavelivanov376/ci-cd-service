package com.projects.cicd.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class RepositoryRequest {

    @NotBlank(message = "URL must not be blank")
    @URL(message = "Invalid URL format")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
