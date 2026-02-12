package com.rapidauth.rapidauthcore.model;

import java.time.Instant;
import java.util.Map;

public interface AuthRole {

    String getId();

    String getName();

    String getDescription();

    Instant getCreatedAt();

    default String getParentRoleId() {
        return null;
    }

    default Map<String, Object> getMetadata() {
        return Map.of();
    }
}
