package com.twitter.kamilyedrzejuq.web

import com.twitter.kamilyedrzejuq.base.IntegrationSpec
import com.twitter.kamilyedrzejuq.infrastructure.MockResponseFactory
import org.springframework.http.MediaType
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec

class WeatherInfoEndpointTest extends IntegrationSpec {

    def "should get weather info"() {

        given: "city name"
        String cityName = "Warsaw"

        and:
        openWeatherMapReturnsSuccessfullyResponse()

        when: "I go to /weather/{cityName}"
        ResponseSpec response = performGetRequest("/weather/{cityName}", cityName)

        then: "response has property values"
        response
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)

        and: "response has expected body"
        response.expectBody()
                .json("""                  
                 {
                     "city":"Warsaw",
                     "pressure":1018,
                     "humidity":32,
                     "temp":27
                 }""")
    }

    private void openWeatherMapReturnsSuccessfullyResponse() {
        mockWebServer.enqueue(MockResponseFactory.returnSuccessfullySampleResponse())
    }
}
