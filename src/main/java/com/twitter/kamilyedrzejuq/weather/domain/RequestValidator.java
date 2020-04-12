package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand;
import io.vavr.control.Either;
import org.apache.commons.lang3.StringUtils;

class RequestValidator {

    Either<String, FetchCityWeatherCommand> validate(FetchCityWeatherCommand command) {
        if (command == null)
            return Either.left("Command can not be null!");
        if (StringUtils.isBlank(command.getCityName()))
            return Either.left("City name can not be null or empty!");
        return Either.right(command);
    }
}