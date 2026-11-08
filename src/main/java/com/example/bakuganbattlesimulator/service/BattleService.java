package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.records.BakuganBattle;
import com.example.bakuganbattlesimulator.records.BakuganStats;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    private final ChatClient chatClient;
    private final BakuganService bakuganService;

    public BattleService(ChatClient.Builder builder, BakuganService bakuganService){
        this.chatClient = builder.build();
        this.bakuganService = bakuganService;
    }


    public BakuganBattle simulateBattleByName(String bakuganA, String bakuganB){
        BakuganStats fighterA = bakuganService.findByName(bakuganA);
        BakuganStats fighterB = bakuganService.findByName(bakuganB);

        return chatClient.prompt()
                .system("""
                You are a legendary, high-energy anime battle commentator for Bakugan brawls. 
                Simulate an epic, dramatic, multi-stage battle between Bakugan A and Bakugan B.
                
                The Strict Rules:
                - `initGPowerA` and `initGPowerB` must match the starting base stats.
                - `battleLog` MUST contain 8 to 10 dramatic, turn-by-turn action steps shouting ability cards in ALL CAPS.
                - `finalGpowerA` and `finalGpowerB` must reflect the final tally after ability boosts and field weather effects.
                - `winner` must state the exact name of the winning Bakugan.
                - `attributeA` and `attributeB` must state respective attributes. 
                - `decisiveFieldFactor` should explain how the live weather data impacted the brawl.
                """)
                .user("BRAWL SETUP"+
                        "Bakugan A: " + fighterA +
                        "\nBakugan B: " + fighterB)
                .call()
                .entity(BakuganBattle.class);
    }
}
