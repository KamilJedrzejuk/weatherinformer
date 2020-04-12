package com.twitter.kamilyedrzejuq.web;

import com.twitter.kamilyedrzejuq.weather.domain.WeatherService;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
record WeatherInfoEndpoint(WeatherService weatherService) {

    @GetMapping("/weather/{cityName}")
    Mono<WeatherInfoResponse> getWeatherInfo(@PathVariable("cityName") final String cityName) {
        FetchCityWeatherCommand requestDTO = new FetchCityWeatherCommand(cityName);
        return weatherService.fetch(Mono.just(requestDTO));
    }
}