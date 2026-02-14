package com.rapidauth.rapidauthcore.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    private String email;
    private String passwordHash;
    private boolean emailVerified;
    private String username;
    private String image;
}
