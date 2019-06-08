package com.twitter.kamilyedrzejuq.infrastructure.mvc;

import com.twitter.kamilyedrzejuq.weather.domain.exception.FetchWeatherException;
import com.twitter.kamilyedrzejuq.weather.domain.exception.RequestValidationException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
class ExceptionHandlingAdvice {


    @ExceptionHandler(FetchWeatherException.class)
    ResponseEntity<ErrorMessage> handle(FetchWeatherException e) {
        Throwable cause = e.getCause();

        if(isClientError(cause))
            return propagateClientError((WebClientResponseException) cause);
        if(isValidationError(cause))
            return validationError((RequestValidationException) cause);
        if(isTimeoutError(cause))
            return timeoutError((ReadTimeoutException) cause);


        return unexpectedError(cause);
    }

    private boolean isClientError(Throwable error) {
        return error instanceof WebClientResponseException;
    }

    private boolean isValidationError(Throwable error){
        return error instanceof RequestValidationException;
    }

    private boolean isTimeoutError(Throwable error) {
        return error instanceof ReadTimeoutException;
    }

    private ResponseEntity<ErrorMessage> propagateClientError(WebClientResponseException e) {
        HttpStatus statusCode = e.getStatusCode();
        ErrorMessage errorMessage = createErrorMessage("Openweathermap error", e.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    private ResponseEntity<ErrorMessage> validationError(RequestValidationException e) {
        HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;
        ErrorMessage errorMessage = createErrorMessage("Validation error", e.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    private ResponseEntity<ErrorMessage> timeoutError(ReadTimeoutException e) {
        HttpStatus statusCode = HttpStatus.GATEWAY_TIMEOUT;
        ErrorMessage errorMessage = createErrorMessage("Timeout error", e.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    private ResponseEntity<ErrorMessage> unexpectedError(Throwable e) {
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = createErrorMessage("Unexpected error", e.getMessage());
        return new ResponseEntity<>(errorMessage, statusCode);
    }

    private ErrorMessage createErrorMessage(String title, String message) {
        return new ErrorMessage(title, message);
    }

    @Getter
    @AllArgsConstructor
    class ErrorMessage {
        private String title;
        private String message;
    }
}
