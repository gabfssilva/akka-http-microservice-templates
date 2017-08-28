package org.akka.templates.endpoints

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.model.HttpResponse
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait HealthCheckEndpoint {
  implicit def executor: ExecutionContextExecutor

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val healthCheckApiRoute: Route = {
    (pathPrefix("api" / "health-check") & logRequestResult("health-check", Logging.InfoLevel)) {
      get {
        complete(HttpResponse(entity = "ok"))
      }
    }
  }
}
