package com.twitter.kamilyedrzejuq.infrastructure.openweathermap

import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class MockResponseFactory {

    static def sample_response_200 = new File(MockResponseFactory.getResource("/openweathermap_response_200.json").toURI()).text
    static def sample_response_404 = new File(MockResponseFactory.getResource("/openweathermap_response_404.json").toURI()).text

    static MockResponse returnSuccessfullySampleResponse() {
        return new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .setResponseCode(200)
                .setBody(sample_response_200)
    }

    static MockResponse returnNotFoundSampleResponse() {
        return new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .setResponseCode(404)
                .setBody(sample_response_404)
    }

}
