package com.example.bakuganbattlesimulator.service;

import com.example.bakuganbattlesimulator.records.BakuganStats;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BakuganService {
    private final ChatClient chatClient;
    private final List<BakuganStats> roster = new ArrayList<>();

    public BakuganService( ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String demo(){
        return chatClient.prompt()
                .system("You are a Bakugan monster designer. Generate a Bakugan with name, attributes, G-Power and specie," +
                        "as well as ability cards for the generated Bakugan")
                .user("Forge a random Bakugan")
                .call()
                .content();
    }

    public BakuganStats createRandomBakugan(){
        BakuganStats newBakugan = chatClient.prompt()
                .system("You are a Bakugan monster designer. Generate a completely random," +
                        "balanced Bakugan creature with G-Power between 300 and 1000," +
                        "including attributes, species, and ability cards.")
                .user("Awake a random Bakugan.")
                .call()
                .entity(BakuganStats.class, spec-> spec.useProviderStructuredOutput());

        roster.add(newBakugan);

        return newBakugan;
    }

    public List<BakuganStats> getRoster() {
        return roster;
    }

    public BakuganStats findByName(String nameSearch){
        return roster.stream()
                .filter(b -> b.name().equalsIgnoreCase(nameSearch))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bakugan not found with name: " + nameSearch))
    }
}
