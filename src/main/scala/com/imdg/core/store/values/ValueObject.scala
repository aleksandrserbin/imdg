package com.imdg.core.store.values

import akka.util.ByteString

/**
  * Created by aserbin on 07/02/2017.
  */
trait ValueObject {

  def toByteString : ByteString

}
