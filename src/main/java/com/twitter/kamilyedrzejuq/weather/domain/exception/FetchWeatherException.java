package com.twitter.kamilyedrzejuq.weather.domain.exception;

public class FetchWeatherException extends RuntimeException {

    public FetchWeatherException(String message) {
        super(message);
    }

    public FetchWeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
