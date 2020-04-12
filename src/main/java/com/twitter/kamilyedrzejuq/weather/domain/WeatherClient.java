package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse;
import reactor.core.publisher.Mono;

public interface WeatherClient {

    Mono<WeatherInfoResponse> fetchWeather(City city);

}