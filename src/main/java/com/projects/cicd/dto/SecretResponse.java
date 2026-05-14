package com.projects.cicd.dto;

import com.projects.cicd.entity.SecretEntity;
import com.projects.cicd.entity.SecretType;

import java.util.UUID;

public class SecretResponse {

    private UUID id;
    private String name;
    private SecretType type;
    private UUID repositoryId;
    private String repositoryUrl;

    public SecretResponse() {
    }

    public SecretResponse(SecretEntity secretEntity) {
        this.id = secretEntity.getId();
        this.name = secretEntity.getName();
        this.type = secretEntity.getType();
        if (secretEntity.getRepository() != null) {
            this.repositoryId = secretEntity.getRepository().getUuid();
            this.repositoryUrl = secretEntity.getRepository().getUrl();
        }
    }

    public UUID getId() {
        return id;
    }

    public SecretResponse setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SecretResponse setName(String name) {
        this.name = name;
        return this;
    }

    public SecretType getType() {
        return type;
    }

    public SecretResponse setType(SecretType type) {
        this.type = type;
        return this;
    }

    public UUID getRepositoryId() {
        return repositoryId;
    }

    public SecretResponse setRepositoryId(UUID repositoryId) {
        this.repositoryId = repositoryId;
        return this;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public SecretResponse setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
        return this;
    }
}
