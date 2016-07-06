package com.otmu.com.otmu.scala.hdfs

import com.otmu.common.config.OtmuSparkConfig
import org.apache.spark.SparkContext

/**
  * Created by SampathS on 14-05-2016.
  */
object SparkHdfs {
  val sc:SparkContext = new SparkContext(new OtmuSparkConfig().getSparkConfig("hdfs spark"))
  val file = sc.textFile("hdfs://namenode:8020/path/to/input")
  val counts = file.flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  counts.saveAsTextFile("hdfs://namenode:8020/output")
}
