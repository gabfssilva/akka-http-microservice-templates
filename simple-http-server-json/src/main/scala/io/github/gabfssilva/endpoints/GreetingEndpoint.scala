package io.github.gabfssilva.endpoints

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import io.github.gabfssilva.response.{Greeting, Response}

import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._

class GreetingEndpoint {
  val greetingRoute: Route = pathPrefix("api" / "greetings") {
    (get & parameters("greeting", "name").as(Greeting)) { greeting =>
      complete(StatusCodes.OK -> Response(greeting.greet))
    }
  }
}
