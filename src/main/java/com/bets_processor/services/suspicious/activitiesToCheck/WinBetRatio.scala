package com.bets_processor.services.suspicious.activitiesToCheck

import com.bets_processor.model.BetScala.{BET, USER_ID, WIN, WIN_BET_RATIO}

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}
import org.springframework.stereotype.Component

@Component
class WinBetRatio {
  def apply(bets: DataFrame):  java.util.List[Row] = {
    bets.select(USER_ID, WIN, BET)
      .groupBy(USER_ID)
      .sum(WIN, BET)
      .withColumn(WIN_BET_RATIO, col("sum(win)")/col("sum(bet)"))
      .filter(col(WIN_BET_RATIO)>0.5)
      .orderBy(USER_ID)
      .collectAsList()
  }

}
