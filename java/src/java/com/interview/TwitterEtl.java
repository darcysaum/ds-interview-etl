package com.interview;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import scala.Tuple2;
import twitter4j.Status;

import java.util.Arrays;

public class TwitterEtl {

    public static void main(String [] args) throws Exception {
        if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
            Logger.getRootLogger().setLevel(Level.WARN);
        }

        SparkConf sparkConf = new SparkConf()
                .setMaster("local[2]")
                .setAppName("Twitter ETL");

        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(10000));

        JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(ssc);
        stream.flatMap(x -> Arrays.asList(x.getText().split(" ")).iterator())
                .mapToPair(s -> new Tuple2<>(s, 1))
                .reduceByKey((x, y) -> x + y)
                .print();

        ssc.start();
        ssc.awaitTermination();
    }
}
