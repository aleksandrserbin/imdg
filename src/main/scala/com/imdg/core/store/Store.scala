package com.imdg.core.store

import com.imdg.core.store.values.{PlainString, ValueObject}

import scala.collection.mutable.Map

/**
  * Data Storage
  * TODO: Store is now a shared state, which is unacceptable in Actor model.
  */
object Store {

  var buckets: Array[Map[String, ValueObject]] = Array.fill[Map[String, ValueObject]](10)(Map[String, ValueObject]())

  def put(key: String, valueObject: ValueObject): Unit = {
    val bucketIndex = calculateIndex(key.hashCode)
    val map = buckets(bucketIndex)
    map += (key -> valueObject)
  }

  def get(key: String) : Option[ValueObject] = {
    val bucket = buckets(calculateIndex(key.hashCode))
    if (bucket contains key) {
      return Some(bucket(key))
    }
    None
  }

  def purge(key : String) : Unit = {
    val bucket = buckets(calculateIndex(key.hashCode))
    bucket -= key
  }

  def calculateIndex(key: Integer) : Integer = {
    return math.abs(key % buckets.length)
  }

  def size() : Array[ValueObject] = {
    buckets flatMap getValues
  }

  private def getValues(map: Map[String, ValueObject] ): Iterable[ValueObject] = {
    map.values
  }

}
