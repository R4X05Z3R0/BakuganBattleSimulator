package com.example.bakuganbattlesimulator.records;

public record WeatherApiResponse(
        String LocationName,
        CurrentData CurrentData,
        DayForecast day0Data,
        DayForecast day1Data,
        DayForecast day2Data
) {
    public record CurrentData(double temperature, String skyText){}
    public record DayForecast(String day, double maxTemp, String skyText){}
}
