package com.imdg.core.executor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import akka.io.Tcp.Write
import akka.util.ByteString
import com.imdg.core.store.Store
import com.imdg.core.store.values.PlainString
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec

/**
  * Actor that executes ADD command.
  * Acceptable syntax is: ADD KEY VALUE
  * If this key already exists, it will not be overwritten
  */
class AddExecutor(connection: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case Exec(options: Array[String], value: String) => {

      if (options.length != 1) {
        connection ! Write(ByteString("Incorrect format: ADD KEY VAL\n"))
        self ! PoisonPill
      } else {
        val key = options(0).trim
        val prev = Store.get(key)
        if (prev.isEmpty) {
          Store.put(key, new PlainString(value))
          connection ! Write(ByteString("OK\n"))
        } else {
          connection ! Write(ByteString("Specified key already exists\n"))
        }
        self ! PoisonPill
      }

    }

  }

}
