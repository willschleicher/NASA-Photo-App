package com.example.nasaphotosapp.client;

import com.example.nasaphotosapp.config.NasaApiConfig;
import com.example.nasaphotosapp.exception.NasaApiException;
import com.example.nasaphotosapp.model.MarsRoverResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NasaApiClient
{

    private final RestClient restClient;
    private final NasaApiConfig nasaApiConfig;

    public NasaApiClient(RestClient restClient, NasaApiConfig nasaApiConfig)
    {
        this.restClient = restClient;
        this.nasaApiConfig = nasaApiConfig;
    }

    public MarsRoverResponse getMarsRoverPhotos(String date)
    {
        String uri = UriComponentsBuilder.fromPath("/mars-photos/api/v1/rovers/curiosity/photos")
                .queryParam("api_key", nasaApiConfig.getApiKey())
                .queryParam("earth_date", date)
                .build()
                .toUriString();

        try
        {
            return restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(MarsRoverResponse.class);
        } catch (Exception e)
        {
            throw new NasaApiException("Error fetching Mars Rover photos for date: " + date, e);
        }
    }
}
