package com.example.bakuganbattlesimulator.records;

import java.util.List;

public record BakuganBattle(
        String bakuganA,
        String bakuganB,
        String attributeA,
        String attributeB,
        int initGPowerA,
        int initGPowerB,
        List<String> battleLog,
        String winner,
        int finalGpowerA,
        int finalGpowerB,
        String matchSummary,
        String decisiveFieldFactor
) {
}
