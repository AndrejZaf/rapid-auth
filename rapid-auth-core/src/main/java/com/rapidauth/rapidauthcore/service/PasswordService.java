package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.model.PasswordStrength;

public interface PasswordService {

    String hashPassword(String password);

    boolean verifyPassword(String password, String hashedPassword);

    boolean isPasswordStrong(String password);

    PasswordStrength checkPasswordStrength(String password);

    void validatePasswordPolicy(String password);
}
