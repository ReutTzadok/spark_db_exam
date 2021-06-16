package com.bets_processor.services.statistics.columnsData

import org.apache.spark.sql.DataFrame
import org.springframework.stereotype.Component

@Component
class ColMinimum {
  def apply(col: DataFrame): Double = col.head().getDouble(0)
}
