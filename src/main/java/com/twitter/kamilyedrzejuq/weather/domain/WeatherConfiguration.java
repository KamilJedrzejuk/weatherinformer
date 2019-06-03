package com.twitter.kamilyedrzejuq.weather.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WeatherConfiguration {

    @Bean
    WeatherService weatherService(WeatherClient weatherClient) {
        return new WeatherService(new RequestValidator(), new DomainMapper(), weatherClient);
    }
}
