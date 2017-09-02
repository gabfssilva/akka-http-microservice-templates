package org.akka.templates.endpoints

import akka.event.Logging
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Route

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait HealthCheckEndpoint extends Endpoint {
  val healthCheckApiRoute: Route = {
    pathPrefix("api" / "health-check") {
      get {
        complete(HttpResponse(entity = "ok"))
      }
    }
  }
}
