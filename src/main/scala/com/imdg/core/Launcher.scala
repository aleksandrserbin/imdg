package com.imdg.core

import akka.actor.{ActorSystem, Props}
import com.imdg.core.configuration.YamlConfigurationSource
import com.imdg.core.store.StoreActor
import com.imdg.core.tcp.{TCPHandler, TCPInterface}

/**
  * Class that launches instance of imdg by means of launching Akka Actor System and
  * creating <code> TCPInterface </code> actor instance to accept TCP connections.
  */
object Launcher {

  def main(args: Array[String]): Unit = {

    val configSource = new YamlConfigurationSource()
    configSource.initConfig()

    val system = ActorSystem("System")

    ActorService.system = system
    val store = ActorService.store

    val tcpInterface = system.actorOf(Props[TCPInterface])

    println("Launched instance")
  }

}
