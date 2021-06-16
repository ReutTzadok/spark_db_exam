package com.bets_processor.controllers;

import com.bets_processor.model.Users;
import com.bets_processor.repositories.BetsRepo;
import com.bets_processor.repositories.UsersRepository;

import com.bets_processor.services.statistics.GameStatistics;
import com.bets_processor.services.suspicious.SuspiciousActivities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

import static org.apache.spark.sql.functions.col;

@RestController
@RequestMapping("/api/")
public class StatisticsController {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private BetsRepo betsRepo;

    @Autowired
    private GameStatistics gameStatistics;
    @Autowired
    private SuspiciousActivities suspiciousActivities;


    @GetMapping("tmp")
    public String hi() {
        betsRepo.getBets().show();

        betsRepo.getBetsWithUserDetails().orderBy("eventId")
                .where(col("eventId").$less(15))
                .orderBy("eventId")
                .show();

        betsRepo.getAsDollarWithUserDetails().orderBy("eventId")
                .where(col("eventId").$less(15))
                .orderBy("eventId")
                .show();

        betsRepo.getAsDollarWithoutDemoFromCountry("USA").orderBy("eventId")
                .where(col("eventId").$less(15))
                .orderBy("eventId")
                .show();

        return "it's works!!";

    }

    // =================== REAL METHODS ==================

    @GetMapping("suspicious")
    public String findSuspiciousActivities(Timestamp startTime, Timestamp endTime) {
        return suspiciousActivities.apply(startTime, endTime);
    }

    @GetMapping(value = "game-statistics", params = "game")
    public String getGameStatistics(Timestamp startTime, Timestamp endTime, String game) {

        return gameStatistics.apply(startTime, endTime, game);
    }

}
