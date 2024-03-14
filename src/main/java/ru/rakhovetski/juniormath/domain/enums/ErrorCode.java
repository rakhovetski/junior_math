package ru.rakhovetski.juniormath.domain.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUBJECT_NOT_FOUND("The subject was not found"),
    TEACHER_NOT_FOUND("The teacher was not found"),
    INCORRECT_TASK_DATA("Incorrect task data"),
    INCORRECT_LOGOUT_TOKEN("Incorrect logout refresh token"),
    INCORRECT_ROLE_NAME("Incorrect role name"),
    USER_REGISTRATION_ERROR("User registration error"),
    USER_NOT_FOUND("User not found"),
    TASK_DOES_NOT_EXISTS("Task does not exist");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
