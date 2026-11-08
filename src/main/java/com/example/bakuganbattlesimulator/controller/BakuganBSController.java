package com.example.bakuganbattlesimulator.controller;

import com.example.bakuganbattlesimulator.records.BakuganBattle;
import com.example.bakuganbattlesimulator.records.BakuganStats;
import com.example.bakuganbattlesimulator.records.BrawlInfo;
import com.example.bakuganbattlesimulator.service.BakuganService;
import com.example.bakuganbattlesimulator.service.BattleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bakugan")
public class BakuganBSController {
    private final BakuganService bakuService;
    private final BattleService battleService;

    public BakuganBSController(BakuganService bakuService, BattleService battleService){
        this.battleService = battleService;
        this.bakuService = bakuService;
    }

    @PostMapping("/demo")
    public String demo(){
        return bakuService.demo();
    }

    @PostMapping("/awake")
    public BakuganStats forgeRandom(){
        return bakuService.createRandomBakugan();
    }

    @GetMapping("/roster")
    public List<BakuganStats> getRoster(){
        return bakuService.getRoster();
    }

    @PostMapping("/brawl")
    public BakuganBattle simulateBrawl(@RequestBody BrawlInfo request){
        return battleService.simulateBattleByName(request.bakuganA(), request.bakuganB());
    }
}
