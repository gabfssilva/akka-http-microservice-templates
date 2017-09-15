package org.akka.templates

import com.typesafe.config.ConfigFactory
import org.akka.templates.model.User
import org.mongodb.scala.MongoClient
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object db {
  type UserCollection = MongoCollection[User]

  val config = ConfigFactory.load()
  val mongoClient: MongoClient = MongoClient(config.getString("mongodb.uri"))

  val codecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)

  val database: MongoDatabase = mongoClient.getDatabase("users").withCodecRegistry(codecRegistry)
  implicit val userCollection: UserCollection = database.getCollection[User]("users")
}
