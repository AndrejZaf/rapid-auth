package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.AuthVerification;
import com.rapidauth.rapidauthcore.model.VerificationType;

import java.util.Optional;

public interface AuthVerificationRepository<V extends AuthVerification>{

    V save(V verification);

    Optional<V> findByIdentifierTokenAndType(String identifierToken, String token, VerificationType type);

    void deleteById(String id);

    int deleteExpiredVerifications();
}
