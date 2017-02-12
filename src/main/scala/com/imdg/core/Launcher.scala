package com.imdg.core

import akka.actor.{ActorSystem, Props}
import com.imdg.core.tcp.{TCPHandler, TCPInterface}

/**
  * Class that launches instance of imdg by means of launching Akka Actor System and
  * creating <code> TCPInterface </code> actor instance to accept TCP connections.
  */
object Launcher {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("System")
    val actor = system.actorOf(Props[TCPInterface])
    println("Launched instance")
  }

}
