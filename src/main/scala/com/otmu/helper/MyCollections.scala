package com.otmu.helper

/**
  * Created by SampathS on 02-05-2016.
  */
object MyCollections {
  def main(args: Array[String]) {
    val data: List[String] = Data.getStringList();
    println("MY First loop and singleton object")
    data.foreach { data =>
      println(data)
    }

    val filterdata = Data.getIntegerList().filter( x => x >5)
   // val dat = for (i <- filterdata) yield { i+i}
     for (j <- filterdata){
       println(j)
     }

    def sum(a:Int ,b:Int , c:Int) = a+b+c
    val b = sum(1,_:Int,3)
    println(b(2));

    def concatString(x:String*): String ={
      val cn:String = ""
      for (i <- 0 to x.length){
        println(x.mkString("/"));
      }
      cn
    }

    concatString("sam","d","c","e")


  }
}