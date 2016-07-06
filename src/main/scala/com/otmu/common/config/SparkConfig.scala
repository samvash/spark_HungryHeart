package com.otmu.common.config

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.spark.SparkConf

/**
  * Created by SampathS on 24-04-2016.
  */
class OtmuSparkConfig extends Serializable {

  def getSparkConfig(appname: String): SparkConf = {
    val sparkConf = new SparkConf().setAppName(appname)
    sparkConf.setMaster("spark://hadoop-single01.dubeng.dnbint.net:7077")
    sparkConf.set("spark.executor.memory", "512m")
    return sparkConf;
  }

  def getWindowsSparkConfig(appname: String): SparkConf = {
    val sparkConf = new SparkConf().setAppName(appname)
    sparkConf.setMaster("local")
    sparkConf.set("spark.driver.allowMultipleContexts", "false")
    sparkConf.set("spark.executor.memory", "1g")
    sparkConf.set("spark.driver.maxResultSize", "1g");

    return sparkConf;
  }

  def getResourcePathForFile(filename:String): String ={
    val url=getClass.getResource("/"+filename);

    return url.getPath;
  }

  def getHbaseConfig(tableName: String,cf: String,qualifier:String): Configuration = {
    val conf = HBaseConfiguration.create()
    System.setProperty("user.name", "hdfs")
    System.setProperty("HADOOP_USER_NAME", "hdfs")
    conf.set("hbase.master", "localhost:60000")
    conf.setInt("timeout", 120000)
    conf.set("hbase.zookeeper.quorum", "localhost")
    conf.set("zookeeper.znode.parent", "/hbase-unsecure")
    conf.set(TableInputFormat.INPUT_TABLE, tableName)
    conf.set(TableInputFormat.SCAN_COLUMN_FAMILY,cf)
    conf.set(TableInputFormat.SCAN_COLUMNS, qualifier)
    return conf;
  }



    def getHBaseConfiguration(tableName:String,cf: String,qualifier:String):Configuration ={
        val hbaseConfiguration = HBaseConfiguration.create()
        hbaseConfiguration.set("hbase.zookeeper.quorum", "hadoop-single01.dubeng.dnbint.net")
        hbaseConfiguration.addResource("C:/Users/sampaths/nodes/hbase-site.xml")
        hbaseConfiguration.set(TableInputFormat.INPUT_TABLE, tableName)
      hbaseConfiguration.set(TableInputFormat.SCAN_COLUMN_FAMILY,cf)
      hbaseConfiguration.set(TableInputFormat.SCAN_COLUMNS, qualifier)
        hbaseConfiguration
    }
}
