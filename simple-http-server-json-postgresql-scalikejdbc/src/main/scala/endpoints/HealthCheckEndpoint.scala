package endpoints

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class HealthCheckEndpoint {
  val healthCheckRoute: Route = {
    pathPrefix("api" / "health-check") {
      get { complete("ok") }
    }
  }
}
