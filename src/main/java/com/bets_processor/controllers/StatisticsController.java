package com.bets_processor.controllers;

import com.bets_processor.services.statistics.GameStatistics;
import com.bets_processor.services.suspicious.SuspiciousActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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


    //example for running: http://localhost:8080/api/game-statistics?startTime=2010-02-22%2016:50:39&endTime=2050-04-22%2016:50:39&game=pocker
    //or: http://localhost:8080/api/game-statistics?startTime=2010-02-22%2016:50:39&endTime=2050-04-22%2016:50:39&game
    @GetMapping("suspicious")
    public String findSuspiciousActivities(Timestamp startTime, Timestamp endTime) {
        return suspiciousActivities.apply(startTime, endTime);
    }

    //example for running: http://localhost:8080/api/suspicious?startTime=2020-02-22%2016:50:39&endTime=2020-08-22%2016:50:39
    @GetMapping(value = "game-statistics", params = "game")
    public String getGameStatistics(Timestamp startTime, Timestamp endTime, String game) {
        return gameStatistics.apply(startTime, endTime, game);
    }

}
