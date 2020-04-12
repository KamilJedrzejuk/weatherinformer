package com.twitter.kamilyedrzejuq.weather.domain.boundary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonPropertyOrder({"city", "temp", "pressure", "humidity"})
public class WeatherInfoResponse {
    String city;
    int temp;
    int pressure;
    int humidity;
}