package com.imdg.core.tcp

/**
  * Akka protocol for TCPInterface
  */
object TCPInterfaceProtocol {
  case class Exec(options: Array[String], value: String)
}
