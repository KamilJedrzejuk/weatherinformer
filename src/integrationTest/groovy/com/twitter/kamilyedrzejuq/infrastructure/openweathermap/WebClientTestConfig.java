package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;
import spock.mock.DetachedMockFactory;

@TestConfiguration
public class WebClientTestConfig {

    private DetachedMockFactory factory = new DetachedMockFactory();

    public static HttpUrl mockWebServerURL;

    @Bean
    MockWebServer mockWebServer(WeatherClientConfig.OpenWeatherMapParam param) {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServerURL = mockWebServer.url(param.getBaseUrl());
        return mockWebServer;
    }

    @Bean
    @DependsOn("mockWebServer")
    @Primary
    WebClient webClientMockServer(WebClient webClient) {
        return webClient.mutate()
                .baseUrl(mockWebServerURL.toString())
                .build();
    }
}
