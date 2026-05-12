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

    public SecretService(SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    public SecretResponse create(SecretRequest request) {

        SecretEntity secretEntity = new SecretEntity();
        secretEntity.setName(request.getName())
                .setType(request.getType())
                .setEncryptedValue(request.getValue()); //TODO: Encrypt the value before saving

        SecretEntity saved = secretRepository.save(secretEntity);

        return new SecretResponse(saved);
    }

    public void delete(UUID id) {
        secretRepository.deleteById(id);
    }
}
