package com.interview

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TwitterEtl {

  def main(args: Array[String]) {

    if (!Logger.getRootLogger.getAllAppenders.hasMoreElements) {
      Logger.getRootLogger.setLevel(Level.WARN)
    }

    //Create a SparkContext to initialize Spark
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("Twitter ETL")

    val ssc = new StreamingContext(conf, Seconds(10))

    val stream = TwitterUtils.createStream(ssc, None)
    stream.flatMap(x => x.getText.split(" "))
        .map((_, 1))
        .reduceByKey((x, y) => x + y)
        .print()

    ssc.start()
    ssc.awaitTermination()
  }
}
