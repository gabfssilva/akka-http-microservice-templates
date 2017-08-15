package org.akka.templates.model

import java.util.concurrent.Executors

import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}


/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
class UserRepository(implicit val session: DBSession) {
  private implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

  def findById(id: Long): Future[Option[User]] = Future {
    sql"""select u.id, u.username, u.age from users u where u.id = $id"""
      .map(rs => User(rs))
      .single()
      .apply()
  }

  def save(user: User): Future[Long] = Future {
    sql"""insert into users (username, age) values (${user.username}, ${user.age})""".updateAndReturnGeneratedKey().apply()
  }
}
