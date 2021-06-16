package com.bets_processor.services.statistics.columnsData

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.sum
import org.springframework.stereotype.Component

@Component
class ColAverage {
  def apply(col: DataFrame, colName: String): Double = col.agg(sum(colName)).first().getDouble(0)/col.count()
}
