package com.bets_processor.model


import scala.language.implicitConversions

case class BetScala(eventId: Int, eventTime: String, eventCountry: String, eventCurrencyCode: String, userId: Int,
                    bet: Double, gameName: String, win: Double, onlineTimeSecs: Int)


object BetScala {
  val EVENT_ID = "eventId"
  val EVENT_TIME = "eventTime"
  val EVENT_COUNTRY = "eventCountry"
  val EVENT_CURRENCY_CODE = "eventCurrencyCode"
  val USER_ID ="userID"
  val BET = "bet"
  val GAME_NAME = "gameName"
  val WIN = "win"
  val ONLINE_TIME_SECS = "onlineTimeSecs"
  val PROFIT = "profit"
  val WIN_BET_RATIO = "winBetRatio"
  val EUR = "EUR"
  val USD = "USD"
}