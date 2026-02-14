package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.AuthUser;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository<U extends AuthUser> {

    U save(U user);

    Optional<U> findById(String id);

    Optional<U> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteById(String id);

    default List<U> findByIds(List<String> ids) {
        return ids.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
