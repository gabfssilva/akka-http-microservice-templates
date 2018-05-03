package models

import scalikejdbc._

object UserSchema extends SQLSyntaxSupport[User] {
  override val tableName = "users"

  def apply(u: ResultName[User])(rs: WrappedResultSet): User = {
    User(rs.long(u.id), rs.string(u.username), rs.int(u.age))
  }
}