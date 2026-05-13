package com.projects.cicd.service;

import com.projects.cicd.dto.SecretRequest;
import com.projects.cicd.dto.SecretResponse;
import com.projects.cicd.entity.SecretEntity;
import com.projects.cicd.repository.SecretRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecretService {

    private final SecretRepository secretRepository;
    private final EncryptionService encryptionService;

    public SecretService(SecretRepository secretRepository, EncryptionService encryptionService) {
        this.secretRepository = secretRepository;
        this.encryptionService = encryptionService;
    }

    public SecretResponse create(SecretRequest request) {

        SecretEntity secretEntity = new SecretEntity();
        secretEntity.setName(request.getName())
                .setType(request.getType())
                .setEncryptedValue(encryptionService.encrypt(request.getValue()));

        SecretEntity saved = secretRepository.save(secretEntity);

        return new SecretResponse(saved);
    }

    public void delete(UUID id) {
        secretRepository.deleteById(id);
    }
}
