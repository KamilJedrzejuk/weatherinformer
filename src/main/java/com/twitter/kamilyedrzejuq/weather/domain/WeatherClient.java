package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherInfoDTO;
import reactor.core.publisher.Mono;

public interface WeatherClient {

    Mono<WeatherInfoDTO> fetchWeather(City city);

}
