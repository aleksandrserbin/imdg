package com.imdg.core.io

import com.imdg.core.tcp.TCPCommand

/**
  * Created by aserbin on 10/02/2017.
  */
case class Command(command: TCPCommand.Value, value: String, rest: Array[String] ) {

  def apply(command: TCPCommand.Value, value: String, rest: Array[String]): Command = Command(command, value, rest)
}
