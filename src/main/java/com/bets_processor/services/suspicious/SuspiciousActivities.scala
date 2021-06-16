package com.bets_processor.services.suspicious

import com.bets_processor.repositories.BetsRepo
import com.bets_processor.services.filters.Filters._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.sql.Timestamp

@Component
class SuspiciousActivities {
  @Autowired
  private val betsRepo: BetsRepo = null

  @Autowired
  private val numberOfCountries: NumberOfCountries = null
  @Autowired
  private val playTime: PlayTime = null
  @Autowired
  private val winBetRatio: WinBetRatio = null


  def apply(startTime: Timestamp, endTime: Timestamp): String = {
    val bets = betsRepo.getAsDollarWithoutDemoFromCountry("USA").between(startTime, endTime)

    val manyCountries: String = numberOfCountries(bets)
    val playTooMuch: String = playTime(bets)
    val winBetRatioTooHigh: String = winBetRatio(bets)

    val summary: String =
    s"""
       |users who play from different countries: $manyCountries
       |users who play too much time: $playTooMuch
       |users with win/bet ratio too high: $winBetRatioTooHigh
       | """.stripMargin


    println(summary)
    summary
  }

}
