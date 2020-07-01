package com.twitter.kamilyedrzejuq.weather.domain.boundary;

import lombok.Value;

@Value(staticConstructor = "of")
public class FetchCityWeatherCommand { //TODO is query
    String cityName;
}
