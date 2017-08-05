package org.akka.templates.response

import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.RejectionHandler

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object rejection {
  implicit def validationHandler: RejectionHandler =
    RejectionHandler.newBuilder().handle {
      case UnprocessableEntityRejection(violations) =>
        val messages = violations.map(x => InvalidParameterMessage(x.path.orNull, x.message))
        complete(unprocessableEntity(Envelop(messages = messages)))
    }.result()
}
