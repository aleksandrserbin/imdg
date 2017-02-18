package com.imdg.core.io

import akka.util.ByteString
import com.imdg.core.tcp.TCPCommand

/**
  * CommandParser split input string onto three components:
  * <ul>
  *   <li> Command - Usually the first word in an input string, which specifies command</li>
  *   <li> Value - Usually the last word, or group of words, surrounded by quotes</li>
  *   <li> Options - Every word between Command and Value</li>
  * </ul>
  */
class CommandParser() {

  def parse(data: ByteString) : Command = {
    val stringRepresentation = data.utf8String.trim
    if (stringRepresentation.endsWith("\"")) {

      var value : String = null
      var processed : Array[String] = null
      var len = stringRepresentation.length

      for (i <- stringRepresentation.length - 1 to 0 by -1) {
        if (stringRepresentation.charAt(i) == '"') {
          value = stringRepresentation substring i
          processed = stringRepresentation substring(0, i) split " "
        }
      }

      value = value substring(1, value.length() - 1) trim()
      value = value replace("\\\"", "\"")

      Command(TCPCommand.valueOf(processed(0) toUpperCase).orNull, value, processed tail)

    } else {

      val parsed = stringRepresentation.split(" ")
      Command(TCPCommand.valueOf(parsed(0) toUpperCase).orNull, parsed last, parsed.slice(1, parsed.length - 1))

    }

  }

}
