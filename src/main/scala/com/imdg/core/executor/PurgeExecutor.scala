package com.imdg.core.executor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.io.Tcp.Write
import akka.util.ByteString
import com.imdg.core.exception.WrongCommandFormat
import com.imdg.core.store.Store
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec

/**
  * Actor that executes PURGE Command.
  * Acceptable syntax is: PURGE KEY
  */
class PurgeExecutor(connection: ActorRef) extends Actor with ActorLogging  {

  override def receive: Receive = {
    case Exec(options: Array[String], key: String) => {

      if (options.length > 0) {
        throw new WrongCommandFormat("PURGE KEY\n")
      } else {
        Store.purge(key)
        connection ! Write(ByteString("OK\n"))
      }

    }
  }

}
