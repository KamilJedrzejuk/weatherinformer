package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import com.twitter.kamilyedrzejuq.infrastructure.openweathermap.WeatherClientConfig.OpenWeatherMapParam;
import com.twitter.kamilyedrzejuq.weather.domain.City;
import com.twitter.kamilyedrzejuq.weather.domain.WeatherClient;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse;
import com.twitter.kamilyedrzejuq.weather.domain.exception.OpenWeatherClientError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
class OpenWeatherMapClientReactiveImpl implements WeatherClient {

    private final WebClient webClient;
    private final OpenWeatherMapParam params;

    @Override
    public Mono<WeatherInfoResponse> fetchWeather(City city) {
        final String uri = "/weather?q={city}&units={units}&appid={appid}";
        return webClient.get()
                .uri(uri, city.cityName(), params.getUnits(), params.getAppId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, mapToDomainException())
                .onStatus(HttpStatus::is5xxServerError, mapToDomainException())
                .bodyToMono(WeatherInfoResponse.class);
    }

    private Function<ClientResponse, Mono<? extends Throwable>> mapToDomainException() {
        return clientResponse -> clientResponse.createException()
                .doOnNext(e -> log.error(e.getMessage(), e))
                .map(e -> new OpenWeatherClientError(e.getMessage(), e));
    }
}