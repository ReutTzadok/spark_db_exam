package com.bets_processor.services.statistics.columnsData

import org.apache.spark.sql.DataFrame
import org.springframework.stereotype.Component

@Component
class ColMaximum {
  def apply(col: DataFrame): Double = col.tail(1).apply(0).getDouble(0)
}
