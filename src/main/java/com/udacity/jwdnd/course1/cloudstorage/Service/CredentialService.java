package com.udacity.jwdnd.course1.cloudstorage.Service;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        String password = credential.getPassword();
        String encodedKey = generateKey();
        String hashedPassword = encryptionService.encryptValue(password, encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(hashedPassword);
        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllEncrypted() {
        return credentialMapper.getAllCredentials();
    }

    public String getUnencrypted(Credential credential) {
        String encryptedPassword = credential.getPassword();
        String key = credential.getKey();
        return encryptionService.encryptValue(encryptedPassword, key);
    }

    public int editCredential(Credential credential) {
        String newPassword = credential.getPassword();
        String newKey = generateKey();
        String encrypedPassword = encryptionService.encryptValue(newPassword, newKey);
        String newUrl = credential.getUrl();
        String newUsername = credential.getUsername();
        return credentialMapper.update(newUrl, newUsername, newKey, encrypedPassword, credential.getCredentialId());
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.delete(credentialId);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}


