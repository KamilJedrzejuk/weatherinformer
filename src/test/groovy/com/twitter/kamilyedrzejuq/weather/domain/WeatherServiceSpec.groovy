package com.twitter.kamilyedrzejuq.weather.domain

import com.twitter.kamilyedrzejuq.weather.domain.boundary.FetchCityWeatherCommand
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoResponse
import com.twitter.kamilyedrzejuq.weather.domain.exception.OpenWeatherClientError
import com.twitter.kamilyedrzejuq.weather.domain.exception.CommandValidationError
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll
import static com.twitter.kamilyedrzejuq.weather.domain.SampleData.*

class WeatherServiceSpec extends Specification {

    WeatherClient weatherClient = Stub()
    WeatherService weatherService = new WeatherConfiguration().weatherService(weatherClient)


    def "should return successfully response"() {

        given: "we have a request with city name"
            FetchCityWeatherCommand request = requestDTO

        and: "weather client returns successfully response"
            weatherClient.fetchWeather(_ as City) >> Mono.just(weatherResponseDTO)

        when: "we ask for weather"
            WeatherInfoResponse weather = fetchWeatherInfo(request)

        then: "system returns weather information"
            with(weather) {
                it.city == city
                it.temp == temperature
                it.pressure == pressure
                it.humidity == humidity
            }
    }

    @Unroll
    def "should throw exception when request is incorrect (cityName - '#cityName')"() {

        given: "we have request with city name"
            FetchCityWeatherCommand request = new FetchCityWeatherCommand(cityName)

        when: "we ask for weather information"
            fetchWeatherInfo(request)

        then: "exception must be thrown"
            def exc = thrown(expectedException)

        and: "it is a validation exception"
            exc.class == CommandValidationError

        where:
            cityName || expectedException
            null     || CommandValidationError
            ""       || CommandValidationError
    }

    def "should throw exception while invoking 3rd party weather service"() {

        given: "we have a request with city name"
            FetchCityWeatherCommand request = requestDTO

        and: "weather client throw any exception"
            weatherClient.fetchWeather(_ as City) >> Mono.error(new OpenWeatherClientError("Some error"))

        when: "we ask for weather information"
            fetchWeatherInfo(request)

        then: "exception must be thrown"
            thrown(OpenWeatherClientError)
    }

    private WeatherInfoResponse fetchWeatherInfo(FetchCityWeatherCommand request) {
        return weatherService.fetch(Mono.just(request)).block()
    }
}
