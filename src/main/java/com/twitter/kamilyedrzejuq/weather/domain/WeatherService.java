package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.dto.CityWeatherRequestDTO;
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherResponseDTO;
import com.twitter.kamilyedrzejuq.weather.domain.exception.FetchWeatherException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WeatherService {

    private final RequestValidator requestValidator;
    private final DomainMapper domainMapper;
    private final WeatherClient weatherClient;

    Mono<WeatherResponseDTO> fetch(Mono<CityWeatherRequestDTO> request) {
        return request.map(requestValidator::validate)
                .map(domainMapper::mapFromDto)
                .flatMap(weatherClient::fetchWeather)
                .onErrorResume(exc -> Mono.error(new FetchWeatherException(exc.getMessage(), exc)));

    }
}
