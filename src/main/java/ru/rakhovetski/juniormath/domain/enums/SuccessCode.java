package ru.rakhovetski.juniormath.domain.enums;

import lombok.Getter;

@Getter
public enum SuccessCode {
    ROOM_SUCCESS_DELETED("The room was successfully deleted"),
    USER_SUCCESS_LOGOUT("User success logout");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }
}
