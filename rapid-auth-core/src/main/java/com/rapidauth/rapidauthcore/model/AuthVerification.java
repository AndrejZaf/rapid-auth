package com.rapidauth.rapidauthcore.model;

import java.time.Instant;

public interface AuthVerification {

    String getId();
    String getIdentifier();
    String getToken();
    VerificationType getType();
    Instant getExpiresAt();
    Instant getCreatedAt();

    default boolean isExpired() {
        return getExpiresAt().isBefore(Instant.now());
    }

    default boolean isValid() {
        return !isExpired();
    }
}
