package com.bets_processor.model

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.language.implicitConversions

case class UserScala(id: Integer, name: String, lastName: String, countryOfOrigin: String, email: String)


object UserScala {
  val ID = "id"
  val NAME = "name"
  val LAST_NAME = "lastName"
  val COUNTRY_OF_ORIGIN = "countryOfOrigin"
  val EMAIL = "email"
}


object UserAdapter {
  implicit def toScala(user: User): UserScala = {
    UserScala(user.getId, user.getName, user.getLastName, user.getCountryOfOrigin, user.getEmail)
  }

  implicit def toScala(users: java.util.List[User]): List[UserScala] = {
    users.asScala.toList.map(user => toScala(user))
  }
}