package com.rapidauth.rapidauthcore.model;

import java.time.Instant;

public interface AuthSession {
    String getId();
    String getUserId();
    String getToken();
    Instant getExpiresAt();
    Instant getCreatedAt();

    default String getIpAddress() { return null; }

    default String getUserAgent() {
        return null;
    }

    default boolean isValid() {
        return getExpiresAt().isAfter(Instant.now());
    }

    default boolean isExpired() {
        return !isValid();
    }
}
