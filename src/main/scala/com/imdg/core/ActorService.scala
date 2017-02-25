package com.imdg.core

import akka.actor.{ActorSystem, Props}
import com.imdg.core.configuration.Configuration
import com.imdg.core.store.StoreActor

/**
  * Is a workaround
  */
// TODO: think about new method of acessing store from executors
object ActorService {

  var system : ActorSystem = _

  lazy val store = system.actorOf(Props(classOf[StoreActor], Configuration.numberOfBuckets))

}
