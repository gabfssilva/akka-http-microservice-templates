package org.akka.templates.model

import org.akka.templates.db.DBContext

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class UserRepository(implicit val ctx: DBContext) {
  import ctx._

  private val users = quote(querySchema[User]("users"))

  def findById(id: Long): Future[Option[User]] =
    run(users.filter(u => u.id == lift(id))).map(_.headOption)

  def save(user: User): Future[Long] =
    run(users.insert(lift(user)).returning(_.id))
}
