package com.twitter.kamilyedrzejuq.weather.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherResponseDTO {
    private String city;
    private int temperature;
    private int pressure;
    private int humidity;
}
