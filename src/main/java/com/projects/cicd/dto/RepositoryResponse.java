package com.projects.cicd.dto;

import com.projects.cicd.entity.RepositoryEntity;

import java.util.UUID;

public class RepositoryResponse {

    private UUID uuid;
    private String url;

    public RepositoryResponse() {
    }

    public RepositoryResponse(RepositoryEntity repositoryEntity) {
        this.uuid = repositoryEntity.getUuid();
        this.url = repositoryEntity.getUrl();
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
