package com.rapidauth.rapidauthcore.repository;

import com.rapidauth.rapidauthcore.model.AuthRole;
import com.rapidauth.rapidauthcore.model.AuthUser;

import java.util.List;
import java.util.Set;

public interface AuthUserRoleRepository<U extends AuthUser, R extends AuthRole> {
    void assignRole(String userId, String roleId);

    void removeRole(String userId, String roleId);

    Set<R> findRolesByUserId(String userId);

    List<U> findUsersByRoleId(String roleId);

    boolean userHasRole(String userId, String roleId);

    void removeAllRolesFromUser(String userId);

    default void assignRoles(String userId, Set<String> roleIds) {
        roleIds.forEach(roleId -> assignRole(userId, roleId));
    }

    default void syncRoles(String userId, Set<String> roleIds) {
        removeAllRolesFromUser(userId);
        assignRoles(userId, roleIds);
    }
}
