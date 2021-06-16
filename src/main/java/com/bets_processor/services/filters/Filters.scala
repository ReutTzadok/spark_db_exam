package com.bets_processor.services.filters

import com.bets_processor.model.BetScala.{BET, EVENT_TIME, PROFIT, WIN}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col

import java.sql.Timestamp

object Filters {
  implicit class FilterBets(bets: DataFrame) {
    def withProfit: DataFrame = bets.withColumn(PROFIT, col(WIN)-col(BET))

    def between(startTime: Timestamp, endTime: Timestamp): DataFrame = bets.where(col(EVENT_TIME).between(startTime, endTime))
  }

}
