package com.imdg.core.executor

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import akka.io.Tcp.Write
import akka.util.ByteString
import com.imdg.core.ActorService
import com.imdg.core.configuration.Configuration
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec


class InfoExecutor(connection : ActorRef) extends Actor with ActorLogging {
  override def receive: Receive = {
    case Exec(options, value) => {
      val response = "Role: " + Configuration.role +
        "\nAll buckets:" + Configuration.numberOfBuckets
      connection ! Write(ByteString(response))
      self ! PoisonPill
    }
  }
}
