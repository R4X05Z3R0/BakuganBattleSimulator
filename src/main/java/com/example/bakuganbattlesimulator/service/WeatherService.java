package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.dto.WeatherDto;
import com.example.bakuganbattlesimulator.records.WeatherApiResponse;
import org.springframework.stereotype.Service;


@Service
public class WeatherService {
    private final WeatherClient client;

    public WeatherService(WeatherClient client) {
        this.client = client;
    }

    public WeatherDto getWeather(String location){
        WeatherApiResponse response = client.fetch(location);
        if (response == null){
            return new WeatherDto(location, 23.0, "Overskyet");
        }
        return toWeatherDto(response);
    }

    private WeatherDto toWeatherDto (WeatherApiResponse raw){
        double temp = (raw.CurrentData() != null) ? raw.CurrentData().temperature() : 0.0;
        String sky = (raw.CurrentData() != null) ? raw.CurrentData().skyText() : "Unknown";

        return new WeatherDto(
                raw.LocationName(),
                temp,
                sky);
    }
}
