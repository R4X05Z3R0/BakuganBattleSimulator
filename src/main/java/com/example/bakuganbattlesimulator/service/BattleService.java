package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.dto.WeatherDto;
import com.example.bakuganbattlesimulator.records.BakuganBattle;
import com.example.bakuganbattlesimulator.records.BakuganStats;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BattleService {

    private final ChatClient chatClient;
    private final BakuganService bakuganService;
    private final WeatherService weatherService;

    public BattleService(ChatClient.Builder builder,
                         BakuganService bakuganService,
                         WeatherService weatherService) {
        this.chatClient = builder.build();
        this.bakuganService = bakuganService;
        this.weatherService = weatherService;
    }


    public BakuganBattle simulateBattleByName(String bakuganA, String bakuganB, String location) {
        BakuganStats fighterA = bakuganService.findByName(bakuganA);
        BakuganStats fighterB = bakuganService.findByName(bakuganB);
        WeatherDto weather = weatherService.getWeather(location);

        return chatClient.prompt()
                .system("""
                        You are a Bakugan battle simulator. Simulate a tactical Bakugan battle using the supplied fighter statistics and battlefield conditions.
                        
                        Keep the output concise, structured, and exciting. Focus on battle mechanics rather than lengthy storytelling.
                        
                        ## BATTLE INTRO
                        Write a short introduction identifying both Bakugan, their attributes, starting G-Power, and the battle location and weather.
                        
                        ## BATTLE LOG
                        Generate 8–10 short, sequential turns, labelled `TURN 1`, `TURN 2`, etc.
                        
                        Each turn describes the Bakugan's action, attacks, GATE CARD or ABILITY CARD activations, their effects, and resulting G-Power changes.
                        
                        Keep descriptions short and focused on battle mechanics.
                        
                        Example:
                        
                        **TURN 1 — BRAWL START!** Both Bakugan are thrown! Veydrake Nox unfolds from its sphere while Volkarion erupts in flames. Both begin at 742 G.
                        
                        **TURN 2 — GATE CARD OPEN! SHADOW SANCTUARY!** The battlefield darkens, granting Darkus a tactical advantage.
                        
                        **TURN 3 — ABILITY CARD ACTIVATE! MOLTEN SPIRAL!** Volkarion launches a blazing attack! Volkarion: 742 → 822 G (+80).
                        
                        **TURN 4 — ABILITY CARD ACTIVATE! UMBRAL COIL!** Veydrake absorbs the incoming energy! Volkarion: 822 → 772 G (-50). Veydrake: 742 → 802 G (+60).
                        
                        **TURN 5 — ABILITY CARD ACTIVATE! FLAME BURST!** Volkarion retaliates with an explosive fire attack! Veydrake: 802 → 732 G (-70).
                        
                        Continue until the decisive finishing turn and battle conclusion.
                        
                        Always display G-Power changes immediately after the ability that causes them.
                        ## BATTLE MECHANICS
                        - Use supplied BakuganStats as the authoritative starting values.
                        - Preserve both starting G-Power values exactly.
                        - Create appropriate abilities based on each Bakugan's attribute.
                        - Apply ability boosts, reductions, negations, and weather effects accurately.
                        - Show G-Power changes explicitly when they occur.
                        - Maintain consistent numerical accounting throughout the battle.
                        - Keep the battle balanced and determine the winner through the actual battle progression.
                        
                        Format stat changes clearly, for example:
                        
                        VEYDRAKE NOX: 742 → 822 G
                        VOLKARION: 742 → 672 G
                        
                        ## BATTLE CONCLUSION
                        Provide the exact winning Bakugan, final G-Power values, a short match summary, and the decisive factor that determined the outcome.
                        
                        ## OUTPUT
                        Return ONLY a valid `BakuganBattle` entity matching the supplied record. Use exact Bakugan names, attributes, and starting statistics. Do not add fields or external commentary.
                        """)
                .user(u -> u.text("""
                                === BRAWL INITIALIZATION ===
                                LOCATION: {location}
                                ATMOSPHERIC CONDITION: {skyText} ({temperature}°C)
                                
                                FIGHTER A (Attacker):
                                {fighterA}
                                
                                FIGHTER B (Defender):
                                {fighterB}
                                
                                COMMENCE THE BRAWL!
                                """)
                        .param("location", location)
                        .param("skyText", weather.skyText())
                        .param("temperature", String.valueOf(weather.temperature()))
                        .param("fighterA", fighterA.toString())
                        .param("fighterB", fighterB.toString()))
                .call()
                .entity(BakuganBattle.class);
    }
}
