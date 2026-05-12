package com.projects.cicd.dto;

import com.projects.cicd.entity.SecretType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SecretRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Type must not be null")
    private SecretType type;

    @NotBlank(message = "Value must not be blank")
    private String value;

    public String getName() {
        return name;
    }

    public SecretRequest setName(String name) {
        this.name = name;
        return this;
    }

    public SecretType getType() {
        return type;
    }

    public SecretRequest setType(SecretType type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SecretRequest setValue(String value) {
        this.value = value;
        return this;
    }
}
