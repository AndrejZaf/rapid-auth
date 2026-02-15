package com.rapidauth.rapidauthcore.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PasswordStrength {

    private StrengthLevel level;

    private int score;

    private boolean meetsRequirements;

    @Builder.Default
    private List<String> messages = new ArrayList<>();

    private int length;

    private boolean hasUppercase;

    private boolean hasLowercase;

    private boolean hasDigit;

    private boolean hasSpecialCharacter;

    private boolean isCommon;

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
