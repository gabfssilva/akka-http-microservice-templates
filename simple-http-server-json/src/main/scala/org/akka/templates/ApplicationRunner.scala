package org.akka.templates

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.{GreetingEndpoint, HealthCheckEndpoint}

import scala.concurrent.ExecutionContextExecutor

import akka.http.scaladsl.server.Directives._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with GreetingEndpoint with HealthCheckEndpoint {
  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher

  import akka.http.scaladsl.Http

  Http().bindAndHandle(apiRoute ~ healthCheckApiRoute, "localhost", 8080)
}
