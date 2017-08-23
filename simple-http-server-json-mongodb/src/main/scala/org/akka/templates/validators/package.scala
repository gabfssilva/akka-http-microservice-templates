package org.akka.templates

import com.wix.accord._
import org.akka.templates.model.{User, UserRequest}

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator {
  import com.wix.accord.dsl._

  implicit val userValidator: Validator[User] = validator[User] { user =>
    user.username is notBlank
    user.age should be > 0
  }

  implicit val userRequestValidator: Validator[UserRequest] = validator[UserRequest] { userRequest =>
    userRequest.id is objectId
  }
}
