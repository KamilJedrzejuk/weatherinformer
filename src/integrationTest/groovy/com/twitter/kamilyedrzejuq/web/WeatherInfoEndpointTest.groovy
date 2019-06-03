package com.twitter.kamilyedrzejuq.web

import com.twitter.kamilyedrzejuq.base.IntegrationSpec
import com.twitter.kamilyedrzejuq.infrastructure.MockResponseFactory
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

class WeatherInfoEndpointTest extends IntegrationSpec {

    def "should get weather info"() {

        given: "city name"
        String cityName = "Warsaw"

        and:
        andOpenWeatherMapReturnsSuccessfullyResponse()

        when: "I go to /weather/{cityName}"
        WebTestClient.ResponseSpec responseSpec = performGetRequest("/weather/{cityName}", cityName)

        then: "response has property values"
        responseSpec
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)

        and: "response has expected body"
        responseSpec.expectBody()
                .json("""                  
                 {
                     "city":"Warsaw",
                     "pressure":1018,
                     "humidity":32,
                     "temp":27
                 }""")
    }

    private void andOpenWeatherMapReturnsSuccessfullyResponse() {
        mockWebServer.enqueue(MockResponseFactory.returnSuccessfullySampleResponse())
    }
}
