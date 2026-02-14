package com.rapidauth.rapidauthcore.model;

import java.util.Set;
import java.util.stream.Collectors;

public interface AuthUserWithRoles extends AuthUser {

    Set<? extends AuthRole> getRoles();

    default boolean hasRole(String roleName) {
        return getRoles().stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    default boolean hasAnyRole(String... roleNames) {
        Set<String> roleNameSet = Set.of(roleNames);
        return getRoles().stream()
                .anyMatch(role -> roleNameSet.contains(role.getName()));
    }

    default boolean hasAllRoles(String... roleNames) {
        return getRoles().stream()
                .map(AuthRole::getName)
                .collect(Collectors.toSet())
                .containsAll(Set.of(roleNames));
    }

    default Set<String> getRoleNames() {
        return getRoles().stream()
                .map(AuthRole::getName)
                .collect(Collectors.toSet());
    }
}
