package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.AuthRole;

import java.util.List;
import java.util.Optional;

public interface AuthRoleRepository<R extends AuthRole> {

    R save(R role);

    Optional<R> findById(String id);

    Optional<R> findByName(String name);

    List<R> findAll();

    void deleteById(String id);

    boolean existsByName(String name);
}
