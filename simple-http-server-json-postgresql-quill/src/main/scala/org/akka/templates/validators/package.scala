package org.akka.templates

import akka.http.scaladsl.server.Directive0
import org.akka.templates.model.User

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator {
  import com.wix.accord.dsl._

  implicit val userValidator = validator[User] { user =>
    user.username is notBlank
    user.age should be > 0
  }

  def validateUser(user: User): Directive0 = validateEntity(user)
}
