package com.twitter.kamilyedrzejuq.weather.domain.exception;

public class OpenWeatherClientError extends RuntimeException {

    public OpenWeatherClientError(String message) {
        super(message);
    }

    public OpenWeatherClientError(String message, Throwable cause) {
        super(message, cause);
    }
}
