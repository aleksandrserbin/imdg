package com.imdg.core.executor


import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}
import akka.io.Tcp.Write
import akka.util.{ByteString, Timeout}
import com.imdg.core.store.Store
import com.imdg.core.store.StoreProtocol.Get
import com.imdg.core.store.values.{PlainString, ValueObject}
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec
import akka.pattern.ask
import com.imdg.core.ActorService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util
import scala.util.{Failure, Success}

/**
  * Actor that executes GET Command.
  * Acceptable syntax is: GET KEY
  */
class GetExecutor(connection: ActorRef) extends Actor with ActorLogging {

  override def receive: Receive = {

    case Exec(options: Array[String], key: String) => {
      implicit val timeout = Timeout(5, TimeUnit.SECONDS)

      if (options.length > 0) {
        connection ! Write(ByteString("Wrong format: GET KEY\n"))
        self ! PoisonPill
      } else {
        val store = ActorService.store
        val response : Future[Option[ValueObject]] = (store ? Get(key)).mapTo[Option[ValueObject]]

        val res = Await.result(response, timeout.duration)
        connection ! Write(res.getOrElse(new PlainString(null)).toByteString)
        self ! PoisonPill
      }

    }

  }

}
