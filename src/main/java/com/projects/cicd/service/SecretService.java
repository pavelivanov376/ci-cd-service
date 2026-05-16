package com.projects.cicd.service;

import com.projects.cicd.dto.SecretRequest;
import com.projects.cicd.dto.SecretResponse;
import com.projects.cicd.dto.ValidateSecretResponse;
import com.projects.cicd.entity.GitProvider;
import com.projects.cicd.entity.RepositoryEntity;
import com.projects.cicd.entity.SecretEntity;
import com.projects.cicd.entity.SecretType;
import com.projects.cicd.repository.RepositoryEntityRepository;
import com.projects.cicd.repository.SecretRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
public class SecretService {

    private final SecretRepository secretRepository;
    private final EncryptionService encryptionService;
    private final RepositoryEntityRepository repositoryEntityRepository;
    private final RestClient restClient;

    public SecretService(SecretRepository secretRepository, EncryptionService encryptionService, RepositoryEntityRepository repositoryEntityRepository, RestClient restClient) {
        this.secretRepository = secretRepository;
        this.encryptionService = encryptionService;
        this.repositoryEntityRepository = repositoryEntityRepository;
        this.restClient = restClient;
    }

    public SecretResponse create(SecretRequest request) {
        RepositoryEntity repository = repositoryEntityRepository.findById(request.getRepositoryId())
                .orElseThrow(() -> new EntityNotFoundException("Repository not found with id: " + request.getRepositoryId()));

        SecretEntity secretEntity = new SecretEntity();
        secretEntity.setName(request.getName())
                .setType(request.getType())
                .setEncryptedValue(encryptionService.encrypt(request.getValue()))
                .setRepository(repository);

        SecretEntity saved = secretRepository.save(secretEntity);
        return new SecretResponse(saved);
    }

    public void delete(UUID id) {
        secretRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ValidateSecretResponse validate(UUID id) {
        SecretEntity secret = secretRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Secret not found with id: " + id));

        GitProvider gitProvider = GitProvider.resolve(secret.getRepository().getUrl());
        if (gitProvider != GitProvider.GITHUB) {
            throw new IllegalArgumentException("Unsupported git provider for validation");
        }

        if (secret.getType() != SecretType.TOKEN) {
            throw new IllegalArgumentException("Unsupported secret type for validation");
        }

        return executeSecretValidation(secret, gitProvider);
    }

    private ValidateSecretResponse executeSecretValidation(SecretEntity secret, GitProvider gitProvider) {
        String authorizationHeader = secret.getAuthPrefix() + encryptionService.decrypt(secret.getEncryptedValue());
        try {
            ResponseEntity<Void> response = restClient.get()
                    .uri(gitProvider.getApiUrl())
                    .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                    .retrieve()
                    .toEntity(Void.class);

            return new ValidateSecretResponse(response.getStatusCode().is2xxSuccessful());
        } catch (RestClientException e) {
            return new ValidateSecretResponse(false);
        }
    }
}
