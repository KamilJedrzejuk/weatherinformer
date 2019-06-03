package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import com.twitter.kamilyedrzejuq.infrastructure.openweathermap.WeatherClientConfig.OpenWeatherMapParam;
import com.twitter.kamilyedrzejuq.weather.domain.City;
import com.twitter.kamilyedrzejuq.weather.domain.WeatherClient;
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
class OpenWeatherMapClientReactiveImpl implements WeatherClient {

    private final WebClient webClient;
    private final OpenWeatherMapParam openWeatherMapParam;


    @Override
    public Mono<WeatherInfoDTO> fetchWeather(City city) {
        return webClient.get()

                .uri("/weather?q={city}&units={units}&appid={appid}",
                        city.getCityName(),
                        openWeatherMapParam.getUnits(),
                        openWeatherMapParam.getAppId())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(WeatherInfoDTO.class);
    }
}
