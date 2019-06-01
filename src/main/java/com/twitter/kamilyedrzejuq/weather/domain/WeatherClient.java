package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherResponseDTO;
import reactor.core.publisher.Mono;

public interface WeatherClient {

    Mono<WeatherResponseDTO> fetchWeather(City city);

}
