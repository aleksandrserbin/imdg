package com.imdg.core.executor

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import akka.io.Tcp.Write
import akka.util.ByteString
import com.imdg.core.store.Store
import com.imdg.core.store.values.PlainString
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec

/**
  * Actor that executes GET Command.
  * Acceptable syntax is: GET KEY
  */
class GetExecutor(connection: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case Exec(options: Array[String], key: String) => {

      if (options.length > 0) {
        connection ! Write(ByteString("Wrong format: GET KEY\n"))
        self ! PoisonPill
      } else {
        connection ! Write(Store.get(key).getOrElse(new PlainString("")).toByteString)
      }

    }

  }

}
