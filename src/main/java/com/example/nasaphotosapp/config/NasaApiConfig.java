package com.example.nasaphotosapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class NasaApiConfig
{

    @Getter
    @Value("${nasa.api.key}")
    private String apiKey;

    @Value("${nasa.api.base-url}")
    private String baseUrl;

    @Bean
    public RestClient nasaRestClient()
    {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}