package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse;

class SampleData {

    static final String city = "London";
    static final int temperature = 22;
    static final int pressure = 1021;
    static final int humidity = 77;

    static final FetchCityWeatherCommand requestDTO = createRequest(city);
    static final WeatherInfoResponse weatherResponseDTO = createResponse(city, temperature, pressure, humidity);

    private static FetchCityWeatherCommand createRequest(String cityName) {
        return new FetchCityWeatherCommand(cityName);
    }

    private static WeatherInfoResponse createResponse(String city, int temp, int pressure, int humidity) {
        return WeatherInfoResponse.builder()
                .city(city)
                .temp(temp)
                .pressure(pressure)
                .humidity(humidity)
                .build();
    }
}
