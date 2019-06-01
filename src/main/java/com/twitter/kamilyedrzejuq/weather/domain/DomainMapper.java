package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.dto.CityWeatherRequestDTO;

class DomainMapper {

    City mapFromDto(CityWeatherRequestDTO request) {
        return new City(request.getCityName());
    }
}
