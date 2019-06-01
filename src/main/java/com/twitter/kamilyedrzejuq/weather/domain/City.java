package com.twitter.kamilyedrzejuq.weather.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class City {
    @NonNull String cityName;
}
