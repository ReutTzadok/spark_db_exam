package com.bets_processor.controllers;

import com.bets_processor.services.statistics.GameStatistics;
import com.bets_processor.services.suspicious.SuspiciousActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
@RequestMapping("/api/")
public class StatisticsController {
    @Autowired
    private GameStatistics gameStatistics;
    @Autowired
    private SuspiciousActivities suspiciousActivities;



    @GetMapping("suspicious")
    public String findSuspiciousActivities(Timestamp startTime, Timestamp endTime) {
        return suspiciousActivities.apply(startTime, endTime);
    }


    @GetMapping(value = "game-statistics", params = "game")
    public String getGameStatistics(Timestamp startTime, Timestamp endTime, String game) {
        return gameStatistics.apply(startTime, endTime, game);
    }

}
