package org.akka.templates

import akka.http.scaladsl.server.Directive0
import org.akka.templates.model.User
import org.validation.scala._
import org.validation.scala.matchers._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator {
  implicit val validator = Validator("user") { user: User =>
    assure("username" ~> (user.username is notBlank)) {
      "username cannot be blank"
    } ~ assure("age" ~> (user.age is higherThan(0))) {
      "age must be higher than 0"
    }
  }

  def validateUser(user: User): Directive0 = validateEntity(user)
}
