package com.example.bakuganbattlesimulator.controller;

import com.example.bakuganbattlesimulator.service.BakuganService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BakuganBSController {
    private final BakuganService service;

    public BakuganBSController(BakuganService service){
        this.service = service;
    }

    @PostMapping("/api/bakugan/demo")
    public String demo(){
        return service.demo();
    }
}
