/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// scalastyle:off println
import org.apache.spark._

import scala.math.random

/** Computes an approximation to pi */
object SparkPi {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Spark Pi")
    sparkConf.setMaster("local")
    sparkConf.set("spark.executor.memory","512m")
    sparkConf.set("spark.eventLog.enabled", "true")
    sparkConf.set("spark.eventLog.dir", "file:///c:/var/tmp")
    sparkConf.set("spark.driver.allowMultipleContexts", "false")
    sparkConf.set("spark.executor.memory", "1g")
    sparkConf.set("spark.driver.maxResultSize", "1g");
    val spark = new SparkContext(sparkConf)
    val slices = 100
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = spark.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y < 1) 1 else 0
    }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    spark.stop()
  }
}
// scalastyle:on println
