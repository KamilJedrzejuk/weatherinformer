package com.twitter.kamilyedrzejuq.weather.domain.boundary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"city", "temp", "pressure", "humidity"})
public class WeatherInfoDTO {
    private String city;
    private int temp;
    private int pressure;
    private int humidity;
}
