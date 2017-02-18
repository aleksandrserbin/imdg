package com.imdg.core.executor

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import akka.io.Tcp.Write
import akka.util.ByteString
import com.imdg.core.ActorService
import com.imdg.core.store.Store
import com.imdg.core.store.StoreProtocol.Put
import com.imdg.core.store.values.PlainString
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec

/**
  * Actor that executes PUT command
  * Acceptable syntax is: PUT KEY VALUE
  */
class PutExecutor(connection: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {


    case Exec(options: Array[String], value: String) => {
      if (options.length != 1) {
        connection ! Write(ByteString("Incorrect format: PUT KEY VAL\n"))
        self ! PoisonPill
      } else {
        val key = options(0).trim
        val store = ActorService.store
        store ! Put(key, new PlainString(value))
        connection ! Write(ByteString("OK\n"))
        self ! PoisonPill
      }
    }

  }
}
