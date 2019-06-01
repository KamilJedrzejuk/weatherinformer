package com.twitter.kamilyedrzejuq.weather.domain;

class WeatherConfiguration {

    WeatherService weatherService(WeatherClient weatherClient) {
        return new WeatherService(new RequestValidator(), new DomainMapper(), weatherClient);
    }
}
