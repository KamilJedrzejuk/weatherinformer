package com.twitter.kamilyedrzejuq.weather.domain.exception;

public class CommandValidationError extends RuntimeException {

    public CommandValidationError(String message) {
        super(message);
    }
}
