package models.repository

import models.{Schemas, User}
import quill.DBContext

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(override val ctx: DBContext)(implicit ec: ExecutionContext) extends Schemas {
  import ctx._

  def findById(id: Long): Future[Option[User]] = {
    def query = quote {
      users.filter(_.id == lift(id))
    }

    run(query).map(_.headOption)
  }

  def save(user: User): Future[Long] = {
    def query = quote {
      users.insert(lift(user)).returning(_.id)
    }

    run(query)
  }
}
