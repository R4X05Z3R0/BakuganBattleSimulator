package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.dto.WeatherDto;
import com.example.bakuganbattlesimulator.records.WeatherApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherClient {
    private final RestClient client;

    public WeatherClient(RestClient.Builder builder){
        client = builder
                .baseUrl("https://vejr.eu")
                .build();
    }

    public WeatherApiResponse fetch(String location){
        return client.get()
                .uri( uri-> uri.path("/api.php")
                        .queryParam("location", location)
                        .queryParam("degree", "C")
                        .build())
                .retrieve()
                .body(WeatherApiResponse.class);
        }

    public String fetchWeatherRaw(String location){
        return client.get()
                .uri(uriBuilder -> uriBuilder.
                        path("/api.php")
                        .queryParam("location", location)
                        .queryParam("degree", "C")
                        .build())
                .retrieve()
                .body(String.class);
    }

}
