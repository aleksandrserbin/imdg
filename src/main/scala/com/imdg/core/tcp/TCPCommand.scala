package com.imdg.core.tcp

/**
  * Enumeration of available TCP commands
  */
object TCPCommand extends Enumeration {

  val PUT,
  GET,
  ADD,
  REPLACE,
  PURGE = Value

  def valueOf(key: String) : Option[Value] = values.find(_.toString == key)
}
