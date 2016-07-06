package com.otmu.com.otmu.mytraits

/**
  * Created by SampathS on 02-05-2016.
  */
trait ExcalmatoryFriendly extends Friendly {

  override def greet(): String = {
    return super.greet() + "!"
  }
}
