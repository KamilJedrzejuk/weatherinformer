package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import com.twitter.kamilyedrzejuq.infrastructure.openweathermap.WeatherClientConfig.OpenWeatherMapParam;
import com.twitter.kamilyedrzejuq.weather.domain.City;
import com.twitter.kamilyedrzejuq.weather.domain.WeatherClient;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
class OpenWeatherMapClientReactiveImpl implements WeatherClient {

    private final WebClient webClient;
    private final OpenWeatherMapParam params;

    @Override
    public Mono<WeatherInfoDTO> fetchWeather(City city) {
        final String uri = "/weather?q={city}&units={units}&appid={appid}";
        return webClient.get()
                .uri(uri, city.getCityName(), params.getUnits(), params.getAppId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(WeatherInfoDTO.class);
    }
}