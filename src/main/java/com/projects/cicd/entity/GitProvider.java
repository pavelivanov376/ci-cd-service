package com.projects.cicd.entity;

public enum GitProvider {

    GITHUB("https://api.github.com/user"),
    UNSUPPORTED("");

    private String apiUrl;

    GitProvider(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public static GitProvider resolve(String repositoryUrl) {
        if (repositoryUrl.contains("github.com")) {
            return GITHUB;
        }
        return UNSUPPORTED;
    }
}
