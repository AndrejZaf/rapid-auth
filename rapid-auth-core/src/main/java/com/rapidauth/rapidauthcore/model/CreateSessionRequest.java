package com.rapidauth.rapidauthcore.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateSessionRequest {

    private String userId;

    private String token;

    private Instant expiresAt;

    private String ipAddress;

    private String userAgent;
}
