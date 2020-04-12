package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse;
import com.twitter.kamilyedrzejuq.weather.domain.exception.CommandValidationError;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WeatherService {

    private final RequestValidator requestValidator;
    private final DomainMapper domainMapper;
    private final WeatherClient weatherClient;

    public Mono<WeatherInfoResponse> fetch(Mono<FetchCityWeatherCommand> request) {
        return request.map(requestValidator::validate)
                .flatMap(errorOrCommand -> errorOrCommand.map(command -> {
                    City city = domainMapper.map(command);
                    return weatherClient.fetchWeather(city);
                }).getOrElseGet(validationMessage -> Mono.error(() -> new CommandValidationError(validationMessage))));

    }
}
