package com.otmu.scala.hbase

import com.otmu.common.config.OtmuSparkConfig
import org.apache.hadoop.hbase.client.{HBaseAdmin, Result}
import org.apache.hadoop.hbase.{HBaseConfiguration, HTableDescriptor}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.spark._

object HBaseRead {
  def main(args: Array[String]) {
    val otmuSparkConfig : OtmuSparkConfig = new OtmuSparkConfig()
    val sparkConf = otmuSparkConfig.getSparkConfig("HBase Read")
    val sc = new SparkContext(sparkConf)
    val conf = HBaseConfiguration.create()
    val tableName = "table1"

    System.setProperty("user.name", "hdfs")
    System.setProperty("HADOOP_USER_NAME", "hdfs")
    conf.set("hbase.master", "localhost:60000")
    conf.setInt("timeout", 120000)
    conf.set("hbase.zookeeper.quorum", "localhost")
    conf.set("zookeeper.znode.parent", "/hbase-unsecure")
    conf.set(TableInputFormat.INPUT_TABLE, tableName)

    val admin = new HBaseAdmin(conf)
    if (!admin.isTableAvailable(tableName)) {
      val tableDesc = new HTableDescriptor(tableName)
      admin.createTable(tableDesc)
    }

    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])
    println("Number of Records found : " + hBaseRDD.count())
    sc.stop()
  }
}