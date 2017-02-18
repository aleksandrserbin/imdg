package com.imdg.core.store

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.imdg.core.store.StoreProtocol.{Get, Put}
import com.imdg.core.store.values.ValueObject

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * TBD
  */
class Bucket extends Actor {

  var content: mutable.Map[String, ValueObject] = mutable.Map()

  override def receive: Receive = {
    case Put(key, value) => content += (key -> value)
    case Get(key) => sender ! content.get(key)
  }


}
