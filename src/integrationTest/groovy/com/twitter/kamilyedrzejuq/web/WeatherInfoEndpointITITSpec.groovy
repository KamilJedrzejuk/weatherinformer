package com.twitter.kamilyedrzejuq.web

import com.twitter.kamilyedrzejuq.base.IntegrationITSpec
import com.twitter.kamilyedrzejuq.infrastructure.openweathermap.MockResponseFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec

class WeatherInfoEndpointITITSpec extends IntegrationITSpec {

    def "should get weather info"() {

        given: "city name"
            String cityName = "Warsaw"

        and:
            openWeatherMapReturnsSuccessfullyResponse()

        when: "I go to /weather/{cityName}"
            ResponseSpec response = performGetRequest("/weather/{cityName}", cityName)

        then: "response has property values"
            response.expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)

        and: "response has expected body"
            response.expectBody()
                    .json("""                  
                    {    "city":"Warsaw",
                         "temp":27,
                         "pressure":1018,
                         "humidity":32
                     }""")
    }

    def "should get response that no city was found"() {

        given: "city name"
            String cityName = "XAXAF"

        and:
            openWeatherMapReturnsNotFoundResponse()

        when: "I go to /weather/{cityName}"
            ResponseSpec response = performGetRequest("/weather/{cityName}", cityName)

        then: "response has property values"
            response.expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)

        and: "response has expected body"
            response.expectBody()
                    .json("""                  
                    {    "code":404,
                         "message":"Not Found"
                     }""")
    }

    def "should get timeout"() {

        given: "city name"
            String cityName = "Warsaw"

        and:
            openWeatherNotResponding()

        when: "I go to /weather/{cityName}"
            ResponseSpec response = performGetRequest("/weather/{cityName}", cityName)

        then: "response has property values"
            response.expectStatus().isEqualTo(HttpStatus.REQUEST_TIMEOUT)
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)

        and: "response has expected body"
            response.expectBody()
                    .json("""                  
                    {    "code":408,
                         "message":"Timeout occurred!"
                     }""")
    }

    private void openWeatherMapReturnsSuccessfullyResponse() {
        mockWebServer.enqueue(MockResponseFactory.returnSuccessfullySampleResponse())
    }

    private void openWeatherMapReturnsNotFoundResponse() {
        mockWebServer.enqueue(MockResponseFactory.returnNotFoundSampleResponse())
    }

    private void openWeatherNotResponding() {
        mockWebServer.enqueue(MockResponseFactory.noResponse())
    }
}