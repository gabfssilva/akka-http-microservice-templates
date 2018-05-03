package models.repository

import models._
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(session: DBSession, executionContext: ExecutionContext) {
  implicit val s = session
  implicit val ec = executionContext

  val u = UserSchema.syntax("u")

  def findById(id: Long): Future[Option[User]] = Future {
    DB readOnly { implicit s =>
      withSQL {
        select
          .from(UserSchema as u)
          .where
          .eq(u.id, id)
      }.map(UserSchema(u.resultName)).single.apply()
    }
  }

  val columns = UserSchema.column

  def save(user: User): Future[Long] = Future {
    withSQL {
      insert.into(UserSchema).namedValues(
        columns.username -> user.username,
        columns.age -> user.age
      )
    }.updateAndReturnGeneratedKey().apply()
  }
}
