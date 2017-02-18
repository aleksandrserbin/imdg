package com.imdg.core.store

import com.imdg.core.store.values.ValueObject

/**
  * Created by aserbin on 12/02/2017.
  */
object StoreProtocol {

  case class Put(key: String, value: ValueObject)
  case class Get(key: String)
  case class Purge(key: String)
  case class Add(key: String, value: ValueObject)

}
