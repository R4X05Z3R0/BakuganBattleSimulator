package com.example.bakuganbattlesimulator.records;

import java.util.List;

public record BakuganStats (
        String name,
        String attribute,
        int gPower,
        List<String> abilityCards
) { }
