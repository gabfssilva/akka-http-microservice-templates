package org.akka.templates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Route.seal
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.{HealthCheckEndpoint, UserEndpoint}
import org.akka.templates.logging.loggableRoute
import org.akka.templates.model.UserRepository

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with UserEndpoint with HealthCheckEndpoint {
  import org.akka.templates.db._
  import org.akka.templates.response.rejection._

  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher
  override val userRepository: UserRepository = new UserRepository()

  val routes: Route = loggableRoute(seal(apiRoute ~ healthCheckApiRoute))

  Http().bindAndHandle(routes, "localhost", 8080)
}
