package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.CityWeatherRequestDTO;
import com.twitter.kamilyedrzejuq.weather.domain.exception.RequestValidationException;
import io.vavr.control.Either;
import org.springframework.util.StringUtils;

class RequestValidator {

    CityWeatherRequestDTO validate(CityWeatherRequestDTO request) {
        return check(request).getOrElseThrow(RequestValidationException::new);
    }

    private Either<String, CityWeatherRequestDTO> check(CityWeatherRequestDTO request) {
        if (request == null)
            return Either.left("Request can not be null!");
        if (StringUtils.isEmpty(request.getCityName()))
            return Either.left("City name can not be null or empty!");
        return Either.right(request);
    }
}
