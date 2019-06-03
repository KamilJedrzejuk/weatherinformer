package com.twitter.kamilyedrzejuq.infrastructure

import okhttp3.mockwebserver.MockResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

class MockResponseFactory {

    static def sample_response_200 = new File(MockResponseFactory.getResource("/openweathermap_response_200.json").toURI()).text

    static MockResponse returnSuccessfullySampleResponse() {
        return new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .setResponseCode(200)
                .setBody(sample_response_200)
    }

}
