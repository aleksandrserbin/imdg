package com.imdg.core.exception

/**
  * Exception to be thrown when input command doesn't match any of acceptable command formats.
  * E.g.: GET WRONG CMD has wrong syntax, as GET command only accepts one argument.
  */
class WrongCommandFormat(format: String) extends RuntimeException {

  def getFormat: String = {
    format
  }

}
