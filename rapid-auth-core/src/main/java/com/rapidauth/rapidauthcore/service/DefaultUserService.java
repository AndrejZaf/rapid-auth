package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.factory.AuthUserFactory;
import com.rapidauth.rapidauthcore.model.AuthUser;
import com.rapidauth.rapidauthcore.model.CreateUserRequest;
import com.rapidauth.rapidauthcore.model.UpdateUserRequest;
import com.rapidauth.rapidauthcore.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService<U extends AuthUser> implements UserService<U> {

    private final AuthUserRepository<U> userRepository;
    private final AuthUserFactory<U> userFactory;

    @Override
    public U createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        U user = userFactory.create(request);
        return userRepository.save(user);
    }

    @Override
    public Optional<U> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<U> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public U updateUser(String userId, UpdateUserRequest request) {
        U user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // TODO: apply the updates
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);

    }

    @Override
    public List<U> findByIds(List<String> ids) {
        return userRepository.findByIds(ids);
    }
}
