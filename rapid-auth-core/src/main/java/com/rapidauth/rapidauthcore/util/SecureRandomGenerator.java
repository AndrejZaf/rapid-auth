package com.rapidauth.rapidauthcore.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureRandomGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    public String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(randomBytes);
        return ENCODER.encodeToString(randomBytes);
    }

    public String generateToken() {
        return generateToken(32);
    }

    public String generateNumericCode(int length) {
        if (length <= 0 || length > 10) {
            throw new IllegalArgumentException("Length must be between 1 and 10");
        }

        int bound = (int) Math.pow(10, length);
        int code = SECURE_RANDOM.nextInt(bound);
        return String.format("%0" + length + "d", code);
    }

    public String generateSixDigitCode() {
        return generateNumericCode(6);
    }
}
