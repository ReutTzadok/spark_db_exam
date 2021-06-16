package com.bets_processor.services.suspicious

import com.bets_processor.repositories.BetsRepo
import com.bets_processor.services.filters.Filters._
import com.bets_processor.services.suspicious.activitiesToCheck.{NumberOfCountries, PlayTime, WinBetRatio}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.sql.Timestamp

@Component
class SuspiciousActivities {
  @Autowired
  private val betsRepo: BetsRepo = null

  @Autowired
  private val findSuspicious: FindSuspicious = null

  @Autowired
  private val numberOfCountries: NumberOfCountries = null
  @Autowired
  private val playTime: PlayTime = null
  @Autowired
  private val winBetRatio: WinBetRatio = null


  def apply(startTime: Timestamp, endTime: Timestamp): String = {
    val bets = betsRepo.getAsDollarWithoutDemoFromCountry("USA").between(startTime, endTime)

    val manyCountries: String = findSuspicious(numberOfCountries.apply, bets)
    val playTooMuch: String = findSuspicious(playTime.apply, bets)
    val winBetRatioTooHigh: String = findSuspicious(winBetRatio.apply, bets)

    val summary: String =
    s"""
       |***** Users with suspicious activities: *****</br>
       |users play from different countries: </br>$manyCountries</br></br>
       |users play too much time: </br>$playTooMuch</br></br>
       |users with win/bet ratio too high: </br>$winBetRatioTooHigh</br></br>
       | """.stripMargin

    summary
  }

}
