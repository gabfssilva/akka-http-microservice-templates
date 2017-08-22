package org.akka.templates.endpoints

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import org.akka.templates.response.{Greeting, _}
import org.akka.templates.validators._
import org.akka.templates.json._

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait GreetingEndpoint {
  implicit def executor: ExecutionContextExecutor

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val loggedRequest = logRequestResult("greetings", Logging.InfoLevel)

  val apiRoute: Route = {
    (pathPrefix("api" / "greetings") & loggedRequest) {
      (get & assure(parameters("greeting", "name").as(Greeting))) { greeting =>
        complete {
          ok(Response(greeting.greet))
        }
      }
    }
  }
}
