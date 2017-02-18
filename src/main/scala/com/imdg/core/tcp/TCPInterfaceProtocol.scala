package com.imdg.core.tcp

import com.imdg.core.store.values.ValueObject

/**
  * Akka protocol for TCPInterface
  */
object TCPInterfaceProtocol {
  case class Exec(options: Array[String], value: String)
  case class Put(key: String, value: ValueObject)
}
