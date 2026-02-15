package com.rapidauth.rapidauthcore.factory;

import com.rapidauth.rapidauthcore.model.AuthUser;
import com.rapidauth.rapidauthcore.model.CreateUserRequest;

public interface AuthUserFactory<U extends AuthUser> {
    U create(CreateUserRequest request);
}
