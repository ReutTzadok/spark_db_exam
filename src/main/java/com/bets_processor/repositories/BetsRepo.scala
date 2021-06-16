package com.bets_processor.repositories

import com.bets_processor.model.BetScala
import com.bets_processor.model.BetScala._
import com.bets_processor.model.UserScala._
import com.bets_processor.model.UserAdapter.toScala


import org.apache.spark.sql.functions.{col, when}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component



@Component
class BetsRepo {
  @Autowired
  private val sparkSession: SparkSession = null

  @Autowired
  private val usersRepository: UsersRepository = null


  def getBets: DataFrame = {
    val schema: StructType = Encoders.product[BetScala].schema
    import sparkSession.implicits._

    sparkSession.read.option("multiline","true")
      .schema(schema)
      .json("data/generated_event.json") //todo use application properties
      .as[BetScala]
      .withColumn(EVENT_TIME, col(EVENT_TIME).cast("timestamp"))
  }

  def getBetsWithUserDetails: DataFrame = {
    import sparkSession.implicits._
    val bets = getBets
    val usersDf = sparkSession.sparkContext.parallelize(usersRepository.findAll()).toDF(ID, NAME, LAST_NAME, COUNTRY_OF_ORIGIN, EMAIL)

    bets.join(usersDf, usersDf(ID) === bets(USER_ID))
      .drop(USER_ID)
      .withColumnRenamed(ID, USER_ID)
  }


  def getAsDollarWithUserDetails: DataFrame = {
    getBetsWithUserDetails
      .withColumn(BET, when(col(EVENT_CURRENCY_CODE) === EUR, col(BET).multiply(1.1)).otherwise(col(BET)))
      .withColumn(WIN, when(col(EVENT_CURRENCY_CODE) === EUR, col(WIN).multiply(1.1)).otherwise(col(WIN)))
      .withColumn(EVENT_CURRENCY_CODE, when(col(EVENT_CURRENCY_CODE) === EUR, USD).otherwise(USD))
  }


  def getAsDollarWithoutDemoFromCountry(country: String): DataFrame = {
    getAsDollarWithUserDetails.where(col(COUNTRY_OF_ORIGIN).notEqual(country).or(!col(GAME_NAME).contains("demo")))
  }

}
