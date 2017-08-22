package org.akka.templates.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.mongodb.scala.bson.ObjectId

import scala.annotation.meta.field

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
case class User(@(JsonIgnore@field) var _id: ObjectId, username: String, age: Int) {
  lazy val id: String = _id.toString
}