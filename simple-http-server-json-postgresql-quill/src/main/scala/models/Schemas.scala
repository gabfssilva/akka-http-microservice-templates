package models

import quill.DBContext

trait Schemas {
  val ctx: DBContext

  import ctx._

  val users = quote {
    querySchema[User]("users",
      _.id -> "id",
      _.username -> "username",
      _.age -> "user_age"
    )
  }
}
