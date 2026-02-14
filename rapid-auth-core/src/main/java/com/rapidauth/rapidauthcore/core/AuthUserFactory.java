package com.rapidauth.rapidauthcore.core;

import com.rapidauth.rapidauthcore.model.AuthUser;

public interface AuthUserFactory<U extends AuthUser> {
    U create(CreateUserRequest request);
}
