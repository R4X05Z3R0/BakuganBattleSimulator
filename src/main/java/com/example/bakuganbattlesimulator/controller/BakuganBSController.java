package com.example.bakuganbattlesimulator.controller;

import com.example.bakuganbattlesimulator.records.BakuganStats;
import com.example.bakuganbattlesimulator.service.BakuganService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BakuganBSController {
    private final BakuganService bakuService;

    public BakuganBSController(BakuganService bakuService){
        this.bakuService = bakuService;
    }

    @PostMapping("/api/bakugan/demo")
    public String demo(){
        return bakuService.demo();
    }

    @PostMapping("api/bakugan/awake")
    public BakuganStats forgeRandom(){
        return bakuService.createRandomBakugan();
    }
}
