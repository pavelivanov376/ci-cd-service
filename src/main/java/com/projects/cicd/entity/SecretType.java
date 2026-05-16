package com.projects.cicd.entity;

public enum SecretType {
    TOKEN("Bearer "),
    USERNAME_PASSWORD("Basic "),
    SSH_KEY("");

    private final String authPrefix;

    SecretType(String authPrefix) {
        this.authPrefix = authPrefix;
    }

    public String getAuthPrefix() {
        return authPrefix;
    }
}
