package com.twitter.kamilyedrzejuq.weather.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherInfoDTO {
    private String city;
    private int temperature;
    private int pressure;
    private int humidity;
}
