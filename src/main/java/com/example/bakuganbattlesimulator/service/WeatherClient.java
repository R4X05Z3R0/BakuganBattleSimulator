package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.records.CurrentData;
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


    public CurrentData fetchWeather(String location){
        return client.get()
                .uri(uriBuilder -> uriBuilder.
                        path("/api.php")
                        .queryParam("location", location)
                        .queryParam("degree", "C")
                        .build())
                .retrieve()
                .body(CurrentData.class);
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
