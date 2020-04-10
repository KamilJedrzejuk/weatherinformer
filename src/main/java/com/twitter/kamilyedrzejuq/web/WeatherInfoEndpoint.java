package com.twitter.kamilyedrzejuq.web;

import com.twitter.kamilyedrzejuq.weather.domain.WeatherService;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.CityWeatherRequestDTO;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.twitter.kamilyedrzejuq.weather.domain.boundary.CityWeatherRequestDTO.of;

@RestController
@RequiredArgsConstructor
class WeatherInfoEndpoint {

    private final WeatherService weatherService;

    @GetMapping("/weather/{cityName}")
    Mono<WeatherInfoDTO> getWeatherInfo(@PathVariable("cityName") final String cityName) {
        CityWeatherRequestDTO requestDTO = of(cityName);
        return weatherService.fetch(Mono.just(requestDTO));
    }
}
