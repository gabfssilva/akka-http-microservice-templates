package org.akka.templates.model

import org.akka.templates.db.UserCollection
import org.bson.types.ObjectId

import org.mongodb.scala._
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class UserRepository(implicit val collection: UserCollection) {
  def save(user: User): Future[String] = {
    user._id = ObjectId.get()

    collection
      .insertOne(user)
      .head()
      .map { _ => user.id }
  }

  def findById(id: String): Future[Option[User]] = {
    collection
      .find(Document("_id" -> new ObjectId(id)))
      .first()
      .head()
      .map(Option(_))
  }
}
