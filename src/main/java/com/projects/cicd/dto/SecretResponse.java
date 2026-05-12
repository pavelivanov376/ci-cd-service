package com.projects.cicd.dto;

import com.projects.cicd.entity.SecretEntity;
import com.projects.cicd.entity.SecretType;

import java.util.UUID;

public class SecretResponse {

    private UUID id;
    private String name;
    private SecretType type;

    public SecretResponse() {
    }

    public SecretResponse(SecretEntity secretEntity) {
        this.id = secretEntity.getId();
        this.name = secretEntity.getName();
        this.type = secretEntity.getType();
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
}
