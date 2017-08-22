package org.akka.templates.model

import org.akka.templates.db.DatabaseExecutorContext
import scalikejdbc._

import scala.concurrent.Future


/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class UserRepository(implicit val session: DBSession, val ec: DatabaseExecutorContext) {

  def findById(id: Long): Future[Option[User]] = Future {
    sql"""select u.id, u.username, u.age from users u where u.id = $id"""
      .map(rs => User(rs))
      .single()
      .apply()
  }

  def save(user: User): Future[Long] = Future {
    sql"""insert into users (username, age) values (${user.username}, ${user.age})"""
      .updateAndReturnGeneratedKey()
      .apply()
  }
}
