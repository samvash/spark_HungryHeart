package com.otmu.scala.hbase

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HTable, Put, HBaseAdmin}
import org.apache.hadoop.hbase.util.Bytes


/**
  * Created by SampathS on 15-05-2016.
  */
object hbasecon {
  def main(args:Array[String]){
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "hadoop-single01.dubeng.dnbint.net")
    HBaseAdmin.checkHBaseAvailable(hbaseConf)

    val table = new HTable(hbaseConf, "otmu-registrations")
    val put = new Put(Bytes.toBytes("myrow"))
    put.add(Bytes.toBytes("r"), Bytes.toBytes("colId1"), Bytes.toBytes("foo"))
  }

}
