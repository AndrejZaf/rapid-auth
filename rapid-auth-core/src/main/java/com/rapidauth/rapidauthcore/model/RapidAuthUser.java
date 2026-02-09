package com.rapidauth.rapidauthcore.model;

import java.time.Instant;
import java.util.Set;

public interface RapidAuthUser {

    String getId();

    void setId(String id);

    String getEmail();

    void setEmail(String email);

    boolean isEmailVerified();

    void setEmailVerified(boolean emailVerified);

    boolean isLocked();

    void setLocked(boolean locked);

    Set<String> getRoles();

    void setRoles(Set<String> roles);

    Instant getCreatedDate();

    void setCreatedDate(Instant createdDate);

    Instant getLastModifiedDate();

    void setLastModifiedDate(Instant lastModifiedDate);
}
