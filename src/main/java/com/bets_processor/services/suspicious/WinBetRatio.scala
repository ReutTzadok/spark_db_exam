package com.bets_processor.services.suspicious

import com.bets_processor.model.BetScala.{BET, EVENT_COUNTRY, USER_ID, WIN, WIN_BET_RATIO}
import com.bets_processor.model.Users
import com.bets_processor.repositories.UsersRepository
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.mutable.ListBuffer

@Component
class WinBetRatio {
  @Autowired
  private val usersRepository: UsersRepository = null

  def apply(bets: DataFrame): String = {
    var suspiciousUsers: ListBuffer[Users] = new ListBuffer[Users];

    val suspiciousIds = bets.select(USER_ID, WIN, BET)
      .groupBy(USER_ID)
      .sum(WIN, BET)
      .withColumn(WIN_BET_RATIO, col("sum(win)")/col("sum(bet)"))
      .filter(col(WIN_BET_RATIO)>0.5)
      .orderBy(USER_ID)
      .collectAsList()

    suspiciousIds.forEach(id => suspiciousUsers+=usersRepository.getById(id.getInt(0)))

    suspiciousUsers.toList.toString()

  }

}
