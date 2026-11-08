package com.example.bakuganbattlesimulator.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class BakuganService {
    private final ChatClient chatClient;

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
}
