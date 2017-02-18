package com.imdg.core.store.values

import akka.util.ByteString

/**
  * Created by aserbin on 08/02/2017.
  */
class PlainString(value: String) extends ValueObject {

  override def toByteString: ByteString = {
    if (value == null) {
      ByteString("\n")
    } else {
      ByteString(value + "\n")
    }
  }

}
