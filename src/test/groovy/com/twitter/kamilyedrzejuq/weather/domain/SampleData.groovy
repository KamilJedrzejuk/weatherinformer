package com.twitter.kamilyedrzejuq.weather.domain

import com.twitter.kamilyedrzejuq.weather.domain.dto.CityWeatherRequestDTO
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherResponseDTO

trait SampleData {

    static final String city = "London"
    static final int temperature = 22
    static final int pressure = 1021
    static final int humidity = 77

    static final CityWeatherRequestDTO requestDTO = createRequest(city)
    static final WeatherResponseDTO weatherResponseDTO = createResponse(city, temperature, pressure, humidity)


    private static CityWeatherRequestDTO createRequest(String cityName) {
        CityWeatherRequestDTO requestDTO = new CityWeatherRequestDTO()
        requestDTO.cityName = cityName
        return requestDTO
    }

    private static WeatherResponseDTO createResponse(String city, int temperature, int pressure, int humidity) {
        return WeatherResponseDTO.builder()
                .city(city)
                .temperature(temperature)
                .pressure(pressure)
                .humidity(humidity)
                .build()
    }
}