package org.akka.templates.validators

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.directives.BasicDirectives.provide
import akka.http.scaladsl.server.directives.RouteDirectives.reject
import com.wix.accord._
import com.wix.accord.ViolationBuilder._
import org.akka.templates.response.rejection.UnprocessableEntityRejection
import org.bson.types.ObjectId

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait BaseValidator {
  def validateAndExtract[T](extractor: Directive1[T])(implicit validator: Validator[T]): Directive1[T] = {
    extractor flatMap { entity =>
      validate(entity) match {
        case Success => provide(entity)
        case Failure(violations) => reject(UnprocessableEntityRejection(violations))
      }
    }
  }

  val objectId = new ObjectIdValidator

  class ObjectIdValidator extends NullSafeValidator[String](ObjectId.isValid, _ -> "is not an object id")
}