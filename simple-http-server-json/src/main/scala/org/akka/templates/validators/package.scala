package org.akka.templates

import akka.http.scaladsl.server.Directive0
import org.akka.templates.response.Greeting

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator {
  import com.wix.accord.dsl._

  implicit val greetingValidator = validator[Greeting] { greeting =>
    greeting.greeting is notBlank
    greeting.name is notBlank
  }

  def validateGreeting(greeting: Greeting): Directive0 = validateEntity(greeting)
}
