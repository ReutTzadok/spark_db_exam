package com.bets_processor.services.suspicious.activitiesToCheck

import com.bets_processor.model.BetScala._

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}
import org.springframework.stereotype.Component

@Component
class PlayTime {
  def apply(bets: DataFrame): java.util.List[Row] = {
    bets.select(ONLINE_TIME_SECS, USER_ID)
      .withColumn("onlineTimeHours", col(ONLINE_TIME_SECS).divide(3600))
      .filter(x => x.getDouble(2) > 5)
      .select(USER_ID)
      .distinct()
      .orderBy(USER_ID)
      .collectAsList()
  }

}
