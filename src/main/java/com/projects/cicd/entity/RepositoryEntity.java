package com.projects.cicd.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

@Entity
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String url;

    public UUID getUuid() {
        return uuid;
    }

    public String getUrl() {
        return url;
    }

    public RepositoryEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}
