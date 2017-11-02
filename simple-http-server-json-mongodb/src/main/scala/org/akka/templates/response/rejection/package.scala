package org.akka.templates.response

import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.RejectionHandler
import com.wix.accord.Descriptions

import scala.concurrent.ExecutionContext

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object rejection {
  implicit def validationHandler(implicit ec: ExecutionContext): RejectionHandler =
    RejectionHandler.newBuilder().handle {
      case UnprocessableEntityRejection(violations) =>
        val messages = violations map { x => InvalidParameterMessage(Descriptions.render(x.path), x.constraint) }
        complete(unprocessableEntity(Envelop(messages = messages)))
    }.result()
}
