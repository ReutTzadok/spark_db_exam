package com.bets_processor.services.suspicious.activitiesToCheck

import com.bets_processor.model.BetScala.{EVENT_COUNTRY, USER_ID}

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}
import org.springframework.stereotype.Component


@Component
class NumberOfCountries {
  def apply(bets: DataFrame): java.util.List[Row] = {
    bets.select(USER_ID, EVENT_COUNTRY)
      .distinct()
      .orderBy(USER_ID)
      .groupBy(USER_ID)
      .count()
      .filter(col("count")>1)
      .orderBy(USER_ID)
      .collectAsList()
  }
}
