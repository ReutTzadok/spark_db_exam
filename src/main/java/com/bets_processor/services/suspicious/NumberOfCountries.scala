package com.bets_processor.services.suspicious

import com.bets_processor.model.BetScala.{EVENT_COUNTRY, USER_ID}
import com.bets_processor.model.Users
import com.bets_processor.repositories.{BetsRepo, UsersRepository}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.mutable.ListBuffer


@Component
class NumberOfCountries {
 @Autowired
  private val usersRepository: UsersRepository = null

  def apply(bets: DataFrame): String = {
    var suspiciousUsers: ListBuffer[Users] = new ListBuffer[Users];

    val suspiciousIds = bets.select(USER_ID, EVENT_COUNTRY)
      .distinct()
      .orderBy(USER_ID)
      .groupBy(USER_ID)
      .count()
      .filter(col("count")>1)
      .orderBy(USER_ID)
      .collectAsList()

    suspiciousIds.forEach(id => suspiciousUsers+=usersRepository.getById(id.getInt(0)))

    suspiciousUsers.toList.toString()
  }
}
