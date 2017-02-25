package com.imdg.core.tcp

import com.imdg.core.configuration.Role

/**
  * Enumeration of available TCP commands
  */
object TCPCommand extends Enumeration {

  case class Val(roles : Array[Role.Value]) extends super.Val {
    def permitted(role : Role.Value) = {
      roles.contains(role)
    }

  }

  val PUT = Val(Array(Role.MASTER))
  val PURGE = Val(Array(Role.MASTER))
  val ADD = Val(Array(Role.MASTER))
  val REPLACE = Val(Array(Role.MASTER))

  val GET = Val(Array(Role.MASTER, Role.SLAVE, Role.MANAGER))
  val INFO = Val(Array(Role.MASTER, Role.SLAVE, Role.MANAGER))

  def valOf(key : String) : Option[Val] = values.find(_.toString == key).asInstanceOf[Option[Val]]

  def valueOf(key: String) : Option[Value] = values.find(_.toString == key)
}
