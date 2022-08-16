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
    private final HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public int createCredential(Credential credential) {
        String password = credential.getPassword();
        String encodedKey = generateKey();
        String hashedPassword = encryptionService.encryptValue(password, encodedKey);
        credential.setKey(encodedKey);
        System.out.println("key=" + encodedKey);
        credential.setPassword(hashedPassword);
        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllEncrypted(Integer userId) {
        return credentialMapper.getAllCredentials(userId);
    }

    public int editCredential(Credential credential) {
        String newPassword = credential.getPassword();
        String newKey = generateKey();
        String encrypedPassword = encryptionService.encryptValue(newPassword, newKey);
        String newUrl = credential.getUrl();
        String newUsername = credential.getUsername();
        return credentialMapper.update(newUrl, newUsername, newKey, encrypedPassword, credential.getCredentialId());
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }


    public Credential getCredential(Integer credential_Id) {
        return credentialMapper.getCredential(credential_Id);
    }

    private String generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}


