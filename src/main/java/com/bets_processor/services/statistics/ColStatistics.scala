package com.bets_processor.services.statistics

import com.bets_processor.services.statistics.columnsData.{ColAverage, ColMaximum, ColMinimum}
import org.apache.spark.sql.DataFrame
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ColStatistics {
  @Autowired
  private val colMinimum: ColMinimum = null
  @Autowired
  private val colMaximum: ColMaximum = null
  @Autowired
  private val colAverage: ColAverage = null


  def apply(bets: DataFrame, col: String): String = {
    val orderedBets = bets.select(col).orderBy(col)

    val minCol: Double = colMinimum(orderedBets)
    val maxCol: Double = colMaximum(orderedBets)
    val aveCol: Double = colAverage(orderedBets, col)

    s"""Column $col:
      |min value in column $col is $minCol
      |max value in column $col is $maxCol
      |average of column $col is $aveCol
      |""".stripMargin
  }

}
