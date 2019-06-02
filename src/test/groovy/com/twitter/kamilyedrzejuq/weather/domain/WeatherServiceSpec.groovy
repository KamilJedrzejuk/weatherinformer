package com.twitter.kamilyedrzejuq.weather.domain

import com.twitter.kamilyedrzejuq.weather.domain.dto.CityWeatherRequestDTO
import com.twitter.kamilyedrzejuq.weather.domain.dto.WeatherInfoDTO
import com.twitter.kamilyedrzejuq.weather.domain.exception.FetchWeatherException
import com.twitter.kamilyedrzejuq.weather.domain.exception.RequestValidationException
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

class WeatherServiceSpec extends Specification implements SampleData {

    WeatherClient weatherClient = Stub()
    WeatherService weatherService = new WeatherConfiguration().weatherService(weatherClient)


    def "should return successfully response"() {

        given: "We have a request with city name"
        CityWeatherRequestDTO request = requestDTO

        and: "weather client can response successfully"
        weatherClient.fetchWeather(_ as City) >> Mono.just(weatherResponseDTO)

        when: "we ask for weather"
        WeatherInfoDTO weather = fetchWeatherInfo(request)

        then: "system returns weather information"
        with(weather) {
            it.city == city
            it.temperature == temperature
            it.pressure == pressure
            it.humidity == humidity
        }
    }

    @Unroll
    def "should throw exception when request is incorrect city - '#cityName'"() {

        given: "we have request with city name"
        CityWeatherRequestDTO request = new CityWeatherRequestDTO(cityName)

        when: "we ask for repository details"
        fetchWeatherInfo(request)

        then: "exception must be thrown"
        def exc = thrown(expectedException)
        exc.getCause().class == RequestValidationException

        where:
        cityName || expectedException
        null     || FetchWeatherException
        ""       || FetchWeatherException
    }

    def "should throw exception while invoking 3rd party weather service"() {

        given: "we have a request with city name"
        CityWeatherRequestDTO request = requestDTO

        and: "weather client throw any exception"
        weatherClient.fetchWeather(_ as City) >> Mono.error(anyException)

        when: "we ask for weather information"
        fetchWeatherInfo(request)

        then: "exception must be thrown"
        thrown(FetchWeatherException)
    }

    private WeatherInfoDTO fetchWeatherInfo(CityWeatherRequestDTO request) {
        return weatherService.fetch(Mono.just(request)).block()
    }
}
