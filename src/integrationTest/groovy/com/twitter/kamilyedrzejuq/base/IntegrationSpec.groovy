package com.twitter.kamilyedrzejuq.base

import com.twitter.kamilyedrzejuq.Application
import com.twitter.kamilyedrzejuq.infrastructure.openweathermap.WebClientTestConfig
import groovy.transform.TypeChecked
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec

@TypeChecked
@SpringBootTest(classes = Application, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(WebClientTestConfig)
@ActiveProfiles("test")
abstract class IntegrationSpec extends Specification {

    @Autowired
    ApplicationContext context

    @Shared
    protected MockWebServer mockWebServer

    protected WebTestClient webTestClient

    @Before
    void setupWebTestClient() {

        this.webTestClient = WebTestClient.bindToApplicationContext(this.context)
                .configureClient()
                .baseUrl('/')
                .build()

        if (mockWebServer == null)
            mockWebServer = context.getBean(MockWebServer)
    }

    def cleanupSpec() {
        if (mockWebServer != null)
            mockWebServer.shutdown()
    }

    protected ResponseSpec performGetRequest(String uri, Object... uriVariables) {
        return ((WebTestClient.RequestBodySpec) webTestClient.get()
                .uri(uri, uriVariables))
                .exchange()
    }

}

