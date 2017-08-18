package org.akka.templates.validators


import akka.http.scaladsl.server.Directives.reject
import akka.http.scaladsl.server.{Directive, Directive0}
import org.akka.templates.response.rejection.UnprocessableEntityRejection
import org.validation.scala._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait BaseValidator {
  def validateEntity[T](entity: T)(implicit validator: Validator[T]): Directive0 = {
    Directive { (inner) =>
      val validations = org.validation.scala.validate(entity)
      if (validations.isEmpty) {
        inner(())
      } else {
        reject(UnprocessableEntityRejection(validations))
      }
    }
  }
}
