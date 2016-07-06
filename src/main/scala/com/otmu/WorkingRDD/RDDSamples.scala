package com.otmu.WorkingRDD

import com.otmu.common.config.OtmuSparkConfig
import org.apache.spark.SparkContext

/**
  * Copyrighted as an unpublished work 2015 D&B.
  * Proprietary and Confidential.  Use, possession and disclosure subject to license agreement.
  * Unauthorized use, possession or disclosure is a violation of D&B's legal rights and may result
  * in suit or prosecution.
  */
object RDDSamples {

  def main(args:Array[String]){
    val otmuSparkConfig : OtmuSparkConfig = new OtmuSparkConfig()
    val sparkConf = otmuSparkConfig.getWindowsSparkConfig("HBase Read")
    val sparkContext = new SparkContext(sparkConf)
    val data = sparkContext.textFile(otmuSparkConfig.getResourcePathForFile("data/employeedetails"))
    val net = data.filter(lines => lines.contains("net"))
    val hbase = data.filter(lines => lines.contains("hbase"))
  //  hbase.union(net).saveAsTextFile("c:/var/result")
    val pp  = data.map(x => addSalary(x)).collect()
    print(pp.mkString("|"))
  }

  def addSalary(amount:String):String ={
   return amount + ",129"
  }
}
