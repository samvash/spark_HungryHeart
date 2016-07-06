package com.otmu.scala.hbase

import com.otmu.common.config.OtmuSparkConfig
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{StringType, StructField, StructType}

/**
  * Created by SampathS on 10-05-2016.
  */
object HbaseSample {
  def main(args: Array[String]) {
    val otmuSparkConfig : OtmuSparkConfig = new OtmuSparkConfig()
    System.setProperty("hadoop.home.dir", "D:/shareFolder/hadoop-2.6.3-sp")
    val sparkConf = otmuSparkConfig.getWindowsSparkConfig("HBase Read")
    val sparkContext = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sparkContext)
    // this is used to implicitly convert an RDD to a DataFrame.
    import sqlContext.implicits._

    val aStruct = new StructType((Array(StructField("notification",StringType,nullable = true))))
    val rdd = sparkContext.newAPIHadoopRDD(
      otmuSparkConfig.getHBaseConfiguration("otmu-notifications","n","n:n"),
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result]
    )
    val dtf = rdd.map(x => Notifications(x.toString())).toDF()

print(dtf.count())
  }

  case class Notifications(jsonvalue:String)
}
