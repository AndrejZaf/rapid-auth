package com.rapidauth.rapidauthcore.service;

import com.rapidauth.rapidauthcore.model.PasswordStrength;
import com.rapidauth.rapidauthcore.model.StrengthLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class DefaultPasswordService implements PasswordService{

    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicy passwordPolicy;

    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^A-Za-z0-9]");

    // Common weak passwords (this is a small subset - in production, use a comprehensive list)
    private static final Set<String> COMMON_PASSWORDS = new HashSet<>(Arrays.asList(
            "password", "123456", "12345678", "qwerty", "abc123", "monkey", "1234567",
            "letmein", "trustno1", "dragon", "baseball", "111111", "iloveyou", "master",
            "sunshine", "ashley", "bailey", "passw0rd", "shadow", "123123", "654321",
            "superman", "qazwsx", "michael", "football"
    ));

    @Override
    public String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        log.debug("Hashing password");
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }

        log.debug("Verifying password");
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    @Override
    public boolean isPasswordStrong(String password) {
        PasswordStrength strength = checkPasswordStrength(password);
        return strength.isMeetsRequirements() &&
                strength.getScore() >= 60;
    }

    @Override
    public PasswordStrength checkPasswordStrength(String password) {
        PasswordStrength.PasswordStrengthBuilder builder = PasswordStrength.builder();

        if (password == null) {
            return builder
                    .level(PasswordStrength.StrengthLevel.VERY_WEAK)
                    .score(0)
                    .meetsRequirements(false)
                    .build();
        }

        int length = password.length();
        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
        boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
        boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();
        boolean isCommon = COMMON_PASSWORDS.contains(password.toLowerCase());

        // Calculate score (0-100)
        int score = 0;

        // Length scoring
        if (length >= 8) score += 20;
        if (length >= 12) score += 10;
        if (length >= 16) score += 10;

        // Character variety scoring
        if (hasUppercase) score += 15;
        if (hasLowercase) score += 15;
        if (hasDigit) score += 15;
        if (hasSpecialChar) score += 15;

        // Penalize common passwords
        if (isCommon) score = Math.max(0, score - 50);

        // Determine strength level
        StrengthLevel level;
        if (score < 20) {
            level = StrengthLevel.VERY_WEAK;
        } else if (score < 40) {
            level = StrengthLevel.WEAK;
        } else if (score < 60) {
            level = StrengthLevel.MODERATE;
        } else if (score < 80) {
            level = StrengthLevel.STRONG;
        } else {
            level = StrengthLevel.VERY_STRONG;
        }

        // Check if meets basic requirements
        boolean meetsRequirements = true;
        PasswordStrength strength = builder
                .level(level)
                .score(score)
                .length(length)
                .hasUppercase(hasUppercase)
                .hasLowercase(hasLowercase)
                .hasDigit(hasDigit)
                .hasSpecialCharacter(hasSpecialChar)
                .isCommon(isCommon)
                .build();

        if (length < passwordPolicy.getMinLength()) {
            strength.addMessage("Password must be at least " + passwordPolicy.getMinLength() + " characters");
            meetsRequirements = false;
        }

        if (length > passwordPolicy.getMaxLength()) {
            strength.addMessage("Password must not exceed " + passwordPolicy.getMaxLength() + " characters");
            meetsRequirements = false;
        }

        if (passwordPolicy.isRequireUppercase() && !hasUppercase) {
            strength.addMessage("Password must contain at least one uppercase letter");
            meetsRequirements = false;
        }

        if (passwordPolicy.isRequireLowercase() && !hasLowercase) {
            strength.addMessage("Password must contain at least one lowercase letter");
            meetsRequirements = false;
        }

        if (passwordPolicy.isRequireDigit() && !hasDigit) {
            strength.addMessage("Password must contain at least one digit");
            meetsRequirements = false;
        }

        if (passwordPolicy.isRequireSpecialChar() && !hasSpecialChar) {
            strength.addMessage("Password must contain at least one special character");
            meetsRequirements = false;
        }

        if (passwordPolicy.isCheckCommonPasswords() && isCommon) {
            strength.addMessage("Password is too common, please choose a stronger password");
            meetsRequirements = false;
        }

        strength.setMeetsRequirements(meetsRequirements);

        return strength;
    }

    @Override
    public void validatePasswordPolicy(String password) {
        PasswordStrength strength = checkPasswordStrength(password);

        if (!strength.isMeetsRequirements()) {
            String errors = String.join(", ", strength.getMessages());
            throw new RuntimeException("Password policy violation: " + errors);
        }
    }
}
