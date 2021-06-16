package com.bets_processor.services.suspicious

import com.bets_processor.model.User
import com.bets_processor.repositories.UsersRepository

import org.apache.spark.sql.{DataFrame, Row}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.mutable.ListBuffer


@Component
class FindSuspicious {
  @Autowired
  private val usersRepository: UsersRepository = null

  def apply(findSuspiciousIds: Function[ DataFrame, java.util.List[Row]], bets:DataFrame):String = {
    var suspiciousUsers: ListBuffer[User] = new ListBuffer[User]

    val suspiciousIds: java.util.List[Row] = findSuspiciousIds(bets)

    suspiciousIds.forEach(id => suspiciousUsers+=usersRepository.getById(id.getInt(0)))

    suspiciousUsers.toList.toString().replaceAll("\\),", ",)</br>")
      .replaceAll("List\\(", "")
      .replaceAll("\\)\\)", ")")
  }
}
