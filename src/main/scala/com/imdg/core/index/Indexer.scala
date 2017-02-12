package com.imdg.core.index

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable
import scala.collection.mutable.RedBlackTree

/**
  * Created by aserbin on 10/02/2017.
  */
class Indexer extends Actor with ActorLogging {

  var tree : mutable.TreeMap[String, String] = _


  override def receive: Receive = {
    case _ => null
  }

}
