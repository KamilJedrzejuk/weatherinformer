package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.CityWeatherRequestDTO;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoDTO;

class SampleData {

    static final String city = "London";
    static final int temperature = 22;
    static final int pressure = 1021;
    static final int humidity = 77;

    static final CityWeatherRequestDTO requestDTO = createRequest(city);
    static final WeatherInfoDTO weatherResponseDTO = createResponse(city, temperature, pressure, humidity);

    static final Throwable anyException = new RuntimeException()
;

    private static CityWeatherRequestDTO createRequest(String cityName) {
        CityWeatherRequestDTO requestDTO = new CityWeatherRequestDTO(cityName);
;        return requestDTO;
    }

    private static WeatherInfoDTO createResponse(String city, int temp, int pressure, int humidity) {
        return WeatherInfoDTO.builder()
                .city(city)
                .temp(temp)
                .pressure(pressure)
                .humidity(humidity)
                .build();
    }
}
