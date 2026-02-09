package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.RapidAuthUser;

import java.util.Optional;

public interface UserRepository<T extends RapidAuthUser> {

    Optional<T> findByEmail(String email);

    Optional<T> findById(String id);

    T save(T user);

    void delete(T user);

    boolean existsByEmail(String email);

    long count();
}
