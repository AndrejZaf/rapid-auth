package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.model.AuthUser;
import com.rapidauth.rapidauthcore.model.CreateUserRequest;
import com.rapidauth.rapidauthcore.model.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService<U extends AuthUser> {

    U createUser(CreateUserRequest request);

    Optional<U> findById(String id);

    Optional<U> findByEmail(String email);

    boolean existsByEmail(String email);

    U updateUser(String userId, UpdateUserRequest request);

    void deleteUser(String id);

    List<U> findByIds(List<String> ids);

    default U getById(String id) {
        return findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    default U getByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
