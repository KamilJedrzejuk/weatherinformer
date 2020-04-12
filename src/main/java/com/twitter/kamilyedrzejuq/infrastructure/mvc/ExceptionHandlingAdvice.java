package com.twitter.kamilyedrzejuq.infrastructure.mvc;

import com.twitter.kamilyedrzejuq.weather.domain.exception.CommandValidationError;
import com.twitter.kamilyedrzejuq.weather.domain.exception.OpenWeatherClientError;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
class ExceptionHandlingAdvice {


    @ExceptionHandler(OpenWeatherClientError.class)
    ResponseEntity<ErrorMessage> handleOpenWeatherClientError(OpenWeatherClientError e) {
        logExc(e);
        Throwable cause = e.getCause();
        if (isWebClientResponseError(cause)) {
            WebClientResponseException exception = (WebClientResponseException) cause;
            HttpStatus statusCode = exception.getStatusCode();
            String statusText = exception.getStatusText();
            ErrorMessage errorMessage = createErrorMessage(statusCode, statusText);
            return new ResponseEntity<>(errorMessage, statusCode);
        }
        return unexpectedError(cause);
    }

    @ExceptionHandler(CommandValidationError.class)
    ResponseEntity<ErrorMessage> handleBadRequest(CommandValidationError e) {
        logExc(e);
        ErrorMessage errorMessage = createErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReadTimeoutException.class)
    ResponseEntity<ErrorMessage> handleTimeout(ReadTimeoutException e) {
        logExc(e);
        HttpStatus statusCode = HttpStatus.REQUEST_TIMEOUT;
        ErrorMessage errorMessage = createErrorMessage(HttpStatus.REQUEST_TIMEOUT, "Timeout occurred!");
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ErrorMessage> handleUnexpected(Throwable e) {
        logExc(e);
        return unexpectedError(e);
    }

    private boolean isWebClientResponseError(Throwable error) {
        return error instanceof WebClientResponseException;
    }

    private ResponseEntity<ErrorMessage> unexpectedError(Throwable e) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = createErrorMessage(statusCode, "Unexpected error!");
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    private ErrorMessage createErrorMessage(HttpStatus code, String message) {
        return new ErrorMessage(code.value(), message);
    }

    @Getter
    @AllArgsConstructor
    static class ErrorMessage {
        private int code;
        private String message;
    }

    private void logExc(Throwable e) {
        log.error(e.getMessage(), e);
    }
}