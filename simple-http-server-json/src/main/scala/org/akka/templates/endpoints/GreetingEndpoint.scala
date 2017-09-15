package org.akka.templates.endpoints

import akka.http.scaladsl.server.Route
import org.akka.templates.response.{Greeting, Response}
import org.akka.templates.validators._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait GreetingEndpoint extends Endpoint {
  val apiRoute: Route = pathPrefix("api" / "greetings") {
    (get & validateAndExtract(parameters("greeting".?, "name".?).as(Greeting))) { greeting =>
      complete {
        ok(Response(greeting.greet))
      }
    }
  }
}
