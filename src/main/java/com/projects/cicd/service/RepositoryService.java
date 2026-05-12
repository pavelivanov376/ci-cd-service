package com.projects.cicd.service;

import com.projects.cicd.dto.RepositoryRequest;
import com.projects.cicd.dto.RepositoryResponse;
import com.projects.cicd.entity.RepositoryEntity;
import com.projects.cicd.repository.RepositoryEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RepositoryService {
    private final RepositoryEntityRepository repositoryEntityRepository;

    public RepositoryService(RepositoryEntityRepository repositoryEntityRepository) {
        this.repositoryEntityRepository = repositoryEntityRepository;
    }

    public RepositoryResponse create(RepositoryRequest repositoryRequest) {
        var repositoryEntity = new RepositoryEntity();
        repositoryEntity.setUrl(repositoryRequest.getUrl());

        RepositoryEntity savedRepository = repositoryEntityRepository.save(repositoryEntity);
        return new RepositoryResponse(savedRepository);
    }

    public Collection<RepositoryResponse> getAll() {
        return repositoryEntityRepository.findAll().stream()
                .map(RepositoryResponse::new)
                .toList();
    }
}
