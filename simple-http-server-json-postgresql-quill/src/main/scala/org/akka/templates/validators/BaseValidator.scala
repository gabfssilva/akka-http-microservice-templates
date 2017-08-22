package org.akka.templates.validators

import akka.http.scaladsl.server.Directives.reject
import akka.http.scaladsl.server.{Directive, Directive0}
import com.wix.accord._
import org.akka.templates.response.rejection.UnprocessableEntityRejection

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait BaseValidator {
  def validateEntity[T](entity: T)(implicit validator: Validator[T]): Directive0 = {
    Directive { (inner) =>
      validate(entity) match {
        case Success => inner(())
        case Failure(violations) => reject(UnprocessableEntityRejection(violations))
      }
    }
  }
}
