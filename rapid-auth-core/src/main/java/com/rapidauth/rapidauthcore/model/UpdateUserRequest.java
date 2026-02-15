package com.rapidauth.rapidauthcore.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {

    private String email;
    private String passwordHash;
    private Boolean emailVerified;
    private String username;
    private String image;
}
