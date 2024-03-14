package ru.rakhovetski.juniormath.domain.enums;

import lombok.Getter;

@Getter
public enum SuccessCode {
    USER_SUCCESS_LOGOUT("User success logout");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }
}
