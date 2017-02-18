package com.imdg.core.tcp

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString

/**
  * Gets TCP connections
  */
class TCPInterface extends Actor with ActorLogging {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 4404))

  override def receive: Receive = {
    case b @ Bound(localAddress) =>
      return null;

    case CommandFailed(_ : Bind) => context stop self

    case c @ Connected(remote, localAddress) =>
      val connection = sender
      val handler = context.actorOf(Props(classOf[TCPHandler], sender, remote))
      connection ! Register(handler, keepOpenOnPeerClosed = true)
  }
}
