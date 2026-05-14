package com.projects.cicd.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "secrets")
public class SecretEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name must not be blank")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Secret type must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SecretType type;

    @NotBlank(message = "Value must not be blank")
    @Column(nullable = false)
    private String encryptedValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repository_id", nullable = false)
    private RepositoryEntity repository;

    public UUID getId() {
        return id;
    }

    public SecretEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SecretEntity setName(String name) {
        this.name = name;
        return this;
    }

    public SecretType getType() {
        return type;
    }

    public SecretEntity setType(SecretType type) {
        this.type = type;
        return this;
    }

    public String getEncryptedValue() {
        return encryptedValue;
    }

    public SecretEntity setEncryptedValue(String encryptedValue) {
        this.encryptedValue = encryptedValue;
        return this;
    }

    public RepositoryEntity getRepository() {
        return repository;
    }

    public SecretEntity setRepository(RepositoryEntity repository) {
        this.repository = repository;
        return this;
    }
}
