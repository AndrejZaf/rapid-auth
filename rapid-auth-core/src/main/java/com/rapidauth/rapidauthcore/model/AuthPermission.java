package com.rapidauth.rapidauthcore.model;

import java.time.Instant;

public interface AuthPermission {

    String getId();
    String getName(); // "user:read", "post:create", "admin:*"
    String getResource(); // "user", "post", "admin"
    String getAction(); // "read", "create", "delete", "*"
    String getDescription();
    Instant getCreatedAt();
}
