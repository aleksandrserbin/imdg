package com.imdg.core.configuration

import com.imdg.core.tcp.TCPCommand._

object Role extends Enumeration {
  val SLAVE,
  MASTER,
  MANAGER = Value

  def valueOf(key: String) : Option[Value] = values.find(_.toString == key)
}
