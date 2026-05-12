package com.projects.cicd.controller;

import com.projects.cicd.dto.SecretRequest;
import com.projects.cicd.dto.SecretResponse;
import com.projects.cicd.service.SecretService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/secrets")
public class SecretController {

    private final SecretService secretService;

    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @PostMapping
    public ResponseEntity<SecretResponse> create(@Valid @RequestBody SecretRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(secretService.create(request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        secretService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
