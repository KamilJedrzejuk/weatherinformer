package com.twitter.kamilyedrzejuq.weather.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityWeatherRequestDTO {
    private String cityName;

    public static CityWeatherRequestDTO of(String cityName) {
        return new CityWeatherRequestDTO(cityName);
    }
}
