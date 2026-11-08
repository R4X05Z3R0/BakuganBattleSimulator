package com.example.bakuganbattlesimulator.service;

import org.springframework.ai.chat.client.ChatClient;

public class BakuganService {
    private final ChatClient chatClient;

    public BakuganService( ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String demo(){
        return chatClient.prompt()
                .system("Generate a Bakugan with name, attributes, G-Power and specie," +
                        "as well as ability cards for the generated Bakugan")
                .user("")
                .call()
                .content();
    }
}
