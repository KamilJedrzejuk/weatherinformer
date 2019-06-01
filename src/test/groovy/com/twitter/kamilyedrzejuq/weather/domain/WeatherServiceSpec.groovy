package com.twitter.kamilyedrzejuq.weather.domain

import com.twitter.kamilyedrzejuq.weather.domain.dto.CityWeatherRequestDTO
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherResponseDTO
import reactor.core.publisher.Mono
import spock.lang.Specification

class WeatherServiceSpec extends Specification implements SampleData {

    WeatherClient weatherClient = Stub()
    WeatherService weatherService = new WeatherConfiguration().weatherService(weatherClient)


    def "should return successfully response"() {

        given: "We have a request with city name"
        CityWeatherRequestDTO request = requestDTO

        and: "weather client can response successfully"
        weatherClient.fetchWeather(_ as City) >> Mono.just(weatherResponseDTO)

        when: "we ask for weather"
        WeatherResponseDTO weather = weatherService.fetch(Mono.just(request)).block()

        then: "system returns weather information"
        with(weather) {
            it.city == city
            it.temperature == temperature
            it.pressure == pressure
            it.humidity == humidity
        }
    }
}
