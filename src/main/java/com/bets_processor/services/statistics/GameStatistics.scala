package com.bets_processor.services.statistics

import com.bets_processor.model.BetScala._
import com.bets_processor.repositories.BetsRepo
import com.bets_processor.services.filters.Filters.FilterBets

import org.apache.spark.sql.functions.col
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.sql.Timestamp


@Component
class GameStatistics {
  @Autowired
  private val betsRepo: BetsRepo = null

  @Autowired
  private val colStatistics: ColStatistics = null


  def apply(startTime: Timestamp, endTime: Timestamp, game: String):String = {
    var bets = betsRepo.getAsDollarWithoutDemoFromCountry("USA").between(startTime, endTime)
    var head = "***** Games Statistics *****"

    if (game != "") {
      head = s"***** $game Statistics *****"
      bets = bets.where(col(GAME_NAME).equalTo(game))
    }

    val bet = colStatistics(bets, BET)
    val win = colStatistics(bets, WIN)
    val profit = colStatistics(bets.withProfit, PROFIT)

   s"${head.toUpperCase}</br>$win</br>$bet</br>$profit"
  }

}
