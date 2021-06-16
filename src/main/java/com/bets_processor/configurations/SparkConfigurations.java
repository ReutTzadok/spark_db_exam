package com.bets_processor.configurations;

import org.apache.spark.sql.SparkSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfigurations {
    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder().master("local[*]").appName("plays").getOrCreate();
    }

}
