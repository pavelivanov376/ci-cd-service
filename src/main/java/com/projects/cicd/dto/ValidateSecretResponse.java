package com.projects.cicd.dto;

public class ValidateSecretResponse {

    private boolean isValid;

    public ValidateSecretResponse() {
    }

    public ValidateSecretResponse(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return isValid;
    }

    public ValidateSecretResponse setValid(boolean valid) {
        isValid = valid;
        return this;
    }
}
