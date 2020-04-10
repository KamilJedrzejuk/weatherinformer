package com.twitter.kamilyedrzejuq.infrastructure.openweathermap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.twitter.kamilyedrzejuq.weather.domain.WeatherClient;
import com.twitter.kamilyedrzejuq.weather.domain.boundary.WeatherInfoDTO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
class WeatherClientConfig {

    @Bean
    ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(WeatherInfoDTO.class, new ResponseMapper());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper objectMapper) {
        return new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON);
    }

    @Bean
    ExchangeStrategies exchangeStrategies(Jackson2JsonDecoder jackson2JsonDecoder) {
        return ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs()
                                .jackson2JsonDecoder(jackson2JsonDecoder)).build();

    }

    @Bean
    TcpClient tcpClient(@Value("${openweather.maxTimeoutMillis}") int timeoutMillis) {
        return TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMillis)
                .doOnConnected(connection ->
                        connection
                                .addHandlerLast(new ReadTimeoutHandler(timeoutMillis, TimeUnit.MILLISECONDS))
                );
    }

    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder, OpenWeatherMapParam openWeatherMapParam,
                        TcpClient tcpClient) {
        return webClientBuilder.baseUrl(openWeatherMapParam.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.from(tcpClient)
                                .followRedirect(true)))
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .filter(logRequest())
                .build();
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    @Bean
    WeatherClient weatherClient(WebClient webClient, OpenWeatherMapParam openWeatherMapParam) {
        return new OpenWeatherMapClientReactiveImpl(webClient, openWeatherMapParam);
    }

    @Data
    @Configuration
    @ConfigurationProperties("openweather")
    static class OpenWeatherMapParam {
        private String baseUrl;
        private String units;
        private String appId;
    }
}