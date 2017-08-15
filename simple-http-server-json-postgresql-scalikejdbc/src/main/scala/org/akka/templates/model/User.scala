package org.akka.templates.model

import scalikejdbc._

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
case class User(id: Long, username: String, age: Int)

object User extends SQLSyntaxSupport[User] {
  override val tableName = "users"

  def apply(rs: WrappedResultSet) = new User(rs.long("id"), rs.string("username"), rs.int("age"))
}