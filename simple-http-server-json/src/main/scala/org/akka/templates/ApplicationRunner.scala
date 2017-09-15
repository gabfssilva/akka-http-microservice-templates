package org.akka.templates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.{GreetingEndpoint, HealthCheckEndpoint}

import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Route.seal

import scala.util.{Failure, Success}

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with GreetingEndpoint with HealthCheckEndpoint {
  import org.akka.templates.response.rejection._
  import org.akka.templates.logging._

  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher

  private val routes: Route = loggableRoute(seal(apiRoute ~ healthCheckApiRoute))

  Http().bindAndHandle(routes, "0.0.0.0", 8080).onComplete {
    case Success(b) => system.log.info(s"application is up and running at ${b.localAddress.getHostName}:${b.localAddress.getPort}")
    case Failure(e) => system.log.error(s"could not start application: {}", e.getMessage)
  }
}
