package com.prestacop.config;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

public class SparkConfiguration {

    private static SparkSession sparkSession;

    private SparkConfiguration(){ sparkSession = buildSparkSession(); }

    public static SparkSession getSparkSession(){
        if(sparkSession == null) sparkSession = buildSparkSession();
        return sparkSession;
    }

    private static SparkSession buildSparkSession(){
        final SparkConf sparkConf = new SparkConf()
                .setAppName("prestacop-drone-control")
                .setMaster(String.format("local[%d]", Runtime.getRuntime().availableProcessors()));
                //.setMaster("yarn");
        final SparkSession spark = SparkSession
                .builder()
                .config(sparkConf)
                .getOrCreate();
        return spark;
    }
}
