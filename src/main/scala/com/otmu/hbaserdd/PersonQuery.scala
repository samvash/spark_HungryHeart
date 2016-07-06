package com.otmu.hbaserdd

import com.otmu.common.config.OtmuSparkConfig
import org.apache.spark.SparkContext

/**
  * Copyrighted as an unpublished work 2015 D&B.
  * Proprietary and Confidential.  Use, possession and disclosure subject to license agreement.
  * Unauthorized use, possession or disclosure is a violation of D&B's legal rights and may result
  * in suit or prosecution.
  */
object PersonQuery {
  def main(args:Array[String]){
    val otmuSparkConfig : OtmuSparkConfig = new OtmuSparkConfig()
    // sc is an existing SparkContext.
    val sparkConf = otmuSparkConfig.getWindowsSparkConfig("HBase Read")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    // this is used to implicitly convert an RDD to a DataFrame.
    import sqlContext.implicits._


    // Create an RDD of Person objects and register it as a table.
    val people = sc.textFile(otmuSparkConfig.getResourcePathForFile("data/people.txt")).map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt)).toDF()
    people.registerTempTable("people")

    // SQL statements can be run by using the sql methods provided by sqlContext.
    val teenagers = sqlContext.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")

    // The results of SQL queries are DataFrames and support all the normal RDD operations.
    // The columns of a row in the result can be accessed by ordinal.
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
  }

  // Define the schema using a case class.
  // Note: Case classes in Scala 2.10 can support only up to 22 fields. To work around this limit,
  // you can use custom classes that implement the Product interface.
  case class Person(name: String, age: Int)
}
