package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.model.AuthSession;
import com.rapidauth.rapidauthcore.model.AuthUser;

import java.util.List;
import java.util.Optional;

public interface SessionService<S extends AuthSession> {

    S createSession(AuthUser user, SessionContext sessionContext);

    Optional<S> getSession(String token);

    Optional<S> findById(String id);

    List<S> getUserSessions(String userId);

    void invalidateSession(String token);

    void invalidateSessionById(String id);

    void invalidateAllUserSessions(String userId);

    int cleanupExpiredSessions();

    S refreshSession(String token);
}
