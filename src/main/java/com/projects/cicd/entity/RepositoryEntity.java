package com.projects.cicd.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank(message = "URL must not be blank")
    @URL(message = "Must be a valid URL")
    @Column(nullable = false, unique = true)
    private String url;

    @OneToMany(mappedBy = "repository", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecretEntity> secrets = new ArrayList<>();

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

    public List<SecretEntity> getSecrets() {
        return secrets;
    }

    public RepositoryEntity setSecrets(List<SecretEntity> secrets) {
        this.secrets = secrets;
        return this;
    }
}
