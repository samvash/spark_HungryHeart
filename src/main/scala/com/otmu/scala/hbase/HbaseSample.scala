package com.otmu.scala.hbase

import com.otmu.common.config.OtmuSparkConfig
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
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

    val aStruct = new StructType((Array(StructField("notification",StringType,nullable = true))))
    val rdd = sparkContext.newAPIHadoopRDD(
      otmuSparkConfig.getHBaseConfiguration("otmu-notifications","n","n:n"),
      classOf[TableInputFormat],
      classOf[ImmutableBytesWritable],
      classOf[Result]
    )
    val result = rdd.map(entry => {
      val res =  entry._2.getValue(Bytes.toBytes("n"),Bytes.toBytes("jsonPath"));
      Notifications(Bytes.toString(res))
    })
    result.saveAsTextFile("D:/Spark/otmu_scala/src/main/resources/out/hbase")
 //   val dt = result.map(x => Notifications(Bytes.toString(x)))
// Bytes.toString(resul.getRow)}
 //   print(dtf.count())
  //  dtf.show(1)
  }
  /*
  def getcolm(): Unit ={
    val dtf = rdd.map(entry =>{val resul = entry._2
      resul.getColumnCells(Bytes.toBytes("n"),Bytes.toBytes("n"))}
    )
    dtf.map(x => x.get(0).getValueArray).foreach(x => println(Bytes.toString(x)))
    print( dtf.count())
  }
*/
  case class Notifications(jsonvalue:String)
}



/*16/07/06 16:55:24 INFO TableInputFormatBase: Input split length: 0 bytes.
S    E00:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬273.DNBCodeValue¬107nn  U�o��[]
D  5 600:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬27¬63nn  U�o��[{"code":"423420","description":"Office Equipment Merchant Wholesalers","typeDescription":"North American Industry Classification System 2012","typeDnBCode":24664,"priority":1},{"code":"5081","description":"Wholesale-commercial machines and equipment","typeDescription":"US Standard Industry Code 1972 - 4 digit (European version)","typeDnBCode":1016,"priority":1},{"code":"1842","description":"Computer & Office Equipment Wholesalers ","typeDescription":"D&B Hoovers Industry Code","typeDnBCode":25838,"priority":1},{"code":"5044","description":"Whol office equipment","typeDescription":"US Standard Industry Code 1987 - 4 digit","typeDnBCode":399,"priority":1},{"code":"50440000","description":"Office equipment","typeDescription":"D&B Standard Industry Code","typeDnBCode":3599,"priority":1},{"code":"F","description":"Wholesale Trade","typeDescription":"D&B Standard Major Industry Code","typeDnBCode":24657,"priority":1},{"code":"4666","description":"Wholesale of other office machinery and equipment","typeDescription":"NACE Revision 2","typeDnBCode":29104,"priority":1}]
D    600:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬36¬90nn  U�o��"2015-09-28"
D    600:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬37¬90nn  U�o��"2013-12-26"
Q    C00:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬41.DNBCodeValue¬64nn  U�o��0
D   	 600:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬41¬64nn  U�o��"Unknown"
F   � 800:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬425¬112nn  U�o��[{"value":4,"informationScopeDescription":"Individual","informationScopeDnBCode":9066,"reliabilityDescription":"Modelled","reliabilityDnBCode":9094,"employeeCategories":[],"trend":[]}]
E   2 700:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬42¬103nn  U�o��[{"description":"Branch/Division","dnbCode":9140}]
I    ;00:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬501.102¬34nn  U�o��[]
E    700:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬72¬103nn  U�o��"764172565"
E    700:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬82¬103nn  U�o��"764172565"
E    700:651544181¬1467068400000¬ed12¬ch4¬UPDATE¬98¬103nn  U�o��1
*/