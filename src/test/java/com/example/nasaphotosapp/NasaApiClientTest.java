package com.example.nasaphotosapp;

import com.example.nasaphotosapp.client.NasaApiClient;
import com.example.nasaphotosapp.config.NasaApiConfig;
import com.example.nasaphotosapp.model.MarsRoverResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NasaApiClientTest {

    @Mock
    private RestClient restClient;

    @Mock
    private NasaApiConfig nasaApiConfig;

    @Mock
    private RestClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private NasaApiClient nasaApiClient;

    @BeforeEach
    void setUp() {
        nasaApiClient = new NasaApiClient(restClient, nasaApiConfig);
        when(nasaApiConfig.getApiKey()).thenReturn("DEMO_KEY");
    }

    @Test
    void shouldGetMarsRoverPhotos() {
        // Arrange
        MarsRoverResponse mockResponse = new MarsRoverResponse();

        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersUriSpec).when(requestHeadersUriSpec).uri(anyString());
        doReturn(responseSpec).when(requestHeadersUriSpec).retrieve();
        doReturn(mockResponse).when(responseSpec).body(MarsRoverResponse.class);

        // Act
        MarsRoverResponse result = nasaApiClient.getMarsRoverPhotos("2017-02-27");

        // Assert
        assertNotNull(result);
    }
}
