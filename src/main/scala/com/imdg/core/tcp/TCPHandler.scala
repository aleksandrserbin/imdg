package com.imdg.core.tcp

import java.net.InetSocketAddress
import java.util.regex.Pattern

import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import akka.actor.Actor.Receive
import akka.actor.SupervisorStrategy.Stop
import akka.io.Tcp._
import akka.util.ByteString
import com.imdg.core.configuration.Configuration
import com.imdg.core.exception.WrongCommandFormat
import com.imdg.core.executor._
import com.imdg.core.io.CommandParser
import com.imdg.core.tcp.TCPInterfaceProtocol.Exec
import com.imdg.core.store.values.ValueObject

import scala.collection.mutable.ArrayBuffer

/**
  * Handles TCP connections
  */
class TCPHandler(connection: ActorRef, remote: InetSocketAddress) extends Actor
  with ActorLogging {

  val parser = new CommandParser()

  context watch connection

  override def receive: Receive = {


    case Received(data) => {

      val command = parser.parse(data)

      val cmd = command.command
      var executor : ActorRef = null

      if (!cmd.permitted(Configuration.role)) {

        connection ! Write(ByteString("Not a master node\n"))
        log.warning("Attempt to perform {} action on {} node", cmd.toString, Configuration.role)

      } else {

        cmd match {

          case TCPCommand.PUT => executor =
            context.actorOf(Props(classOf[PutExecutor], connection))

          case TCPCommand.GET => executor =
            context.actorOf(Props(classOf[GetExecutor], connection))

          case TCPCommand.PURGE => executor =
            context.actorOf(Props(classOf[PurgeExecutor], connection))

          case TCPCommand.ADD => executor =
            context.actorOf(Props(classOf[AddExecutor], connection))

          case TCPCommand.INFO => executor =
            context.actorOf(Props(classOf[InfoExecutor], connection))

          case _ => sender ! Write(ByteString("Unknown command\n"))
        }

        connection ! Register(executor, keepOpenOnPeerClosed = true)
        executor ! Exec(command.rest, command.value)

      }


    }

    case PeerClosed => context stop self
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case e: WrongCommandFormat  => {
      connection ! Write(ByteString("Wrong format: " + e.getFormat + "\n"))
      Stop
    }
  }

}
