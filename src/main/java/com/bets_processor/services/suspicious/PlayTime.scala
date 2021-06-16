package com.bets_processor.services.suspicious

import com.bets_processor.model.Users
import com.bets_processor.model.BetScala._
import com.bets_processor.repositories.UsersRepository
import org.apache.arrow.vector.types.pojo.ArrowType.Timestamp
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.col
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import scala.collection.mutable.ListBuffer

@Component
class PlayTime {
  @Autowired
  private val usersRepository: UsersRepository = null

  def apply(bets: DataFrame): String = {
    var suspiciousUsers: ListBuffer[Users] = new ListBuffer[Users];


    val suspiciousIds = bets.select(ONLINE_TIME_SECS, USER_ID)
      .withColumn("onlineTimeHours", col(ONLINE_TIME_SECS).divide(3600))
      .filter(x => x.getDouble(2) > 5)
      .select(USER_ID)
      .distinct()
      .orderBy(USER_ID)
      .collectAsList()

    suspiciousIds.forEach(id => suspiciousUsers+=usersRepository.getById(id.getInt(0)))

    suspiciousUsers.toList.toString()
  }

}
