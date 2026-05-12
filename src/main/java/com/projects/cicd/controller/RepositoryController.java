package com.projects.cicd.controller;

import com.projects.cicd.dto.RepositoryRequest;
import com.projects.cicd.dto.RepositoryResponse;
import com.projects.cicd.service.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping
    public ResponseEntity<RepositoryResponse> create(@Valid @RequestBody RepositoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repositoryService.create(request));
    }

    @GetMapping
    public ResponseEntity<Collection<RepositoryResponse>> getAll() {
        return ResponseEntity.ok(repositoryService.getAll());
    }

}
