package com.imdg.core.store

import akka.actor.Actor.Receive
import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import com.imdg.core.configuration.Configuration
import com.imdg.core.store.StoreProtocol.Get
import com.imdg.core.store.StoreProtocol.Put

/**
  * Created by aserbin on 12/02/2017.
  */
class StoreActor(numberOfBuckets: Int) extends Actor with ActorLogging {

  val buckets : Array[ActorRef] = new Array[ActorRef](numberOfBuckets)


  override def preStart(): Unit = {
    for (i <- 0 until numberOfBuckets) {
      val bucket = context.actorOf(Props(classOf[Bucket]), i toString)
      buckets(i) = bucket
    }
  }

  override def receive: Receive = {

    case Put(key, value) => {
      val bucketIndex = calculateIndex(key.hashCode)
      buckets(bucketIndex) ! Put(key, value)
    }

    case Get(key) => {
      val bucketIndex = calculateIndex(key.hashCode)
      buckets(bucketIndex) forward Get(key)
    }

  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _ => Resume
  }

  def calculateIndex(key: Integer) : Integer = {
    math.abs(key % buckets.length)
  }


}
