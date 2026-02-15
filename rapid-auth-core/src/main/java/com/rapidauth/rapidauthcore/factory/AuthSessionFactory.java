package com.rapidauth.rapidauthcore.factory;

import com.rapidauth.rapidauthcore.model.AuthSession;
import com.rapidauth.rapidauthcore.model.CreateSessionRequest;

public interface AuthSessionFactory<S extends AuthSession> {

    S create(CreateSessionRequest request);
}
