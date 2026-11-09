package com.example.bakuganbattlesimulator.dto;

public record WeatherDto(String location,
                         double temperature,
                         String skyText) {
}
