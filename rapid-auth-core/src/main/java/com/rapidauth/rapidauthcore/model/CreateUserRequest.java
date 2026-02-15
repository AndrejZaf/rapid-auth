package com.rapidauth.rapidauthcore.model;

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
