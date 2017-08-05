package org.akka.templates.endpoints

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.akka.templates.response.Greeting

import scala.concurrent.ExecutionContextExecutor

import org.akka.templates.response._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait GreetingEndpoint {
  implicit def executor: ExecutionContextExecutor

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val apiRoute: Route = {
    pathPrefix("api" / "greetings") {
      (get & parameters("greeting", "name").as(Greeting)) { greeting =>
        complete {
          ok(Response(greeting.greet))
        }
      }
    }
  }
}
