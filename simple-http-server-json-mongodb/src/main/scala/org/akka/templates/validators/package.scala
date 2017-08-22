package org.akka.templates

import com.wix.accord.Validator
import org.akka.templates.model.User

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator {
  import com.wix.accord.dsl._

  implicit val userValidator: Validator[User] = validator[User] { user =>
    user.username is notBlank
    user.age should be > 0
  }
}
