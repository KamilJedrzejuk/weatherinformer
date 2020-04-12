package com.twitter.kamilyedrzejuq.weather.domain;

import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand;

class DomainMapper {

    City map(FetchCityWeatherCommand request) {
        return new City(request.getCityName());
    }
}
