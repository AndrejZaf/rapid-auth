package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.AuthSession;

import java.util.List;
import java.util.Optional;

public interface AuthSessionRepository<S extends AuthSession> {
    S save(S session);

    Optional<S> findById(String id);

    Optional<S> findByToken(String token);

    List<S> findByUserId(String userId);

    void deleteById(String id);

    void deleteByUserId(String userId);

    int deleteExpiredSessions();
}
