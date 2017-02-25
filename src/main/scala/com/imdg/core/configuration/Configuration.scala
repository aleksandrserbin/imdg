package com.imdg.core.configuration

import scala.beans.BeanProperty

object Configuration {

  // default props ?
  var configuration : Configuration = _

  lazy val tcpPort : Int  = { Option.apply(configuration).getOrElse(4404).asInstanceOf[Int]  }

  lazy val role : Role.Value = {
    Role.valueOf(Option.apply(configuration.role)
        .getOrElse("master")
        .toString.toUpperCase())
      .getOrElse(Role.MASTER)
  }

  lazy val numberOfBuckets = {Option.apply(configuration.buckets).getOrElse(100) }

}

class Configuration() {

  @BeanProperty var port : Int = _
  @BeanProperty var role : String  = _
  @BeanProperty var buckets : Integer = _

}

