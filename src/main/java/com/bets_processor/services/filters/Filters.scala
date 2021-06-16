package com.bets_processor.services.filters

import com.bets_processor.model.BetScala.{BET, EVENT_TIME, PROFIT, WIN, WIN_BET_RATIO}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

import java.sql.Timestamp


object Filters {
  implicit class FilterBets(bets: DataFrame) {
    def withProfit: DataFrame = bets.withColumn(PROFIT, col(WIN)-col(BET))

    //todo calculate. remove?
    def withWinBetRatio: DataFrame = bets.withColumn(WIN_BET_RATIO, col(WIN)/col(BET))

    def between(startTime: Timestamp, endTime: Timestamp): DataFrame = bets.where(col(EVENT_TIME).between(startTime, endTime))
  }

}
