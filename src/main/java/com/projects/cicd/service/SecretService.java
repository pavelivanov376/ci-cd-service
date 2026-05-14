package com.projects.cicd.service;

import com.projects.cicd.dto.SecretRequest;
import com.projects.cicd.dto.SecretResponse;
import com.projects.cicd.entity.RepositoryEntity;
import com.projects.cicd.entity.SecretEntity;
import com.projects.cicd.repository.RepositoryEntityRepository;
import com.projects.cicd.repository.SecretRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecretService {

    private final SecretRepository secretRepository;
    private final EncryptionService encryptionService;
    private final RepositoryEntityRepository repositoryEntityRepository;

    public SecretService(SecretRepository secretRepository, EncryptionService encryptionService, RepositoryEntityRepository repositoryEntityRepository) {
        this.secretRepository = secretRepository;
        this.encryptionService = encryptionService;
        this.repositoryEntityRepository = repositoryEntityRepository;
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
}
