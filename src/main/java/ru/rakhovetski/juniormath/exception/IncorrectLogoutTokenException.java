package ru.rakhovetski.juniormath.exception;

public class IncorrectLogoutTokenException extends RuntimeException {

    public IncorrectLogoutTokenException(String message) {
        super(message);
    }
}
