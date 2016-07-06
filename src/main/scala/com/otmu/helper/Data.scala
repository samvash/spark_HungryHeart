package com.otmu.helper

/**
  * Created by SampathS on 02-05-2016.
  */
class Data {

}

object Data{
  type mytype = List[String]
  val mashable =  List[String]("sam","developer","chennai");
  val integerList = List[Int](1,2,3,4,5,6,7,8,9,10);
  def getStringList():mytype={
    Data.mashable;
  }
  def getIntegerList():List[Int] ={
    return integerList
  }

}