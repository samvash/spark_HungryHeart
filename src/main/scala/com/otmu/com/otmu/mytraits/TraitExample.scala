package com.otmu.com.otmu.mytraits

/**
  * Created by SampathS on 02-05-2016.
  */
object TraitExample {
  def main(args:Array[String]){

    var dog = new Animal();
    println(dog.greet())
    dog = new Animal() with  Friendly
    println(dog.greet());
    dog = new Animal with ExcalmatoryFriendly
    println (dog.greet())

  }

}
