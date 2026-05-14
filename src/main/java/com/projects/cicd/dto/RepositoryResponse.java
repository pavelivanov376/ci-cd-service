package com.projects.cicd.dto;

import com.projects.cicd.entity.RepositoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryResponse {

    private UUID uuid;
    private String url;
    private List<SecretResponse> secrets = new ArrayList<>();

    public RepositoryResponse() {
    }

    public RepositoryResponse(RepositoryEntity repositoryEntity) {
        this.uuid = repositoryEntity.getUuid();
        this.url = repositoryEntity.getUrl();
        this.secrets = repositoryEntity.getSecrets().stream()
                .map(SecretResponse::new)
                .toList();
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

    public List<SecretResponse> getSecrets() {
        return secrets;
    }

    public RepositoryResponse setSecrets(List<SecretResponse> secrets) {
        this.secrets = secrets;
        return this;
    }
}
