package com.projects.cicd.service;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final StringEncryptor encryptor;

    public EncryptionService(StringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
