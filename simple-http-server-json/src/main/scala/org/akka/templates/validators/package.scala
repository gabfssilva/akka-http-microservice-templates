package org.akka.templates

import akka.http.scaladsl.server.Directive0
import org.akka.templates.response.Greeting
import org.validation.scala._
import org.validation.scala.matchers._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object validators extends BaseValidator{
  implicit val validator = Validator { greeting: Greeting =>
    assure("greeting" ~> (greeting.greeting is notBlank)) {
      "greeting cannot be blank"
    } ~ assure("name" ~> (greeting.name is notBlank)) {
      "name cannot be blank"
    }
  }

  def validateGreeting(greeting: Greeting): Directive0 = validateEntity(greeting)
}
