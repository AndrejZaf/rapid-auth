package com.rapidauth.rapidauthcore.model;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public interface AuthUser {

    String getId();
    String getEmail();
    boolean isEmailVerified();
    Instant getPasswordHash();
    Instant getCreatedAt();
    Instant getUpdatedAt();

    default String getUsername() {
        return null;
    }

    default String getImage() {
        return null;
    }
}
