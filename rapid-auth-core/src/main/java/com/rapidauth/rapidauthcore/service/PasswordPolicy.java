package com.rapidauth.rapidauthcore.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "rapidauth.password-policy")
public class PasswordPolicy {
    private int minLength = 8;

    private int maxLength = 128;

    private boolean requireUppercase = true;

    private boolean requireLowercase = true;

    private boolean requireDigit = true;

    private boolean requireSpecialChar = true;

    private boolean checkCommonPasswords = true;

    private boolean checkBreachedPasswords = false;

    private int passwordHistorySize = 0;

    private String algorithm = "bcrypt";

}
