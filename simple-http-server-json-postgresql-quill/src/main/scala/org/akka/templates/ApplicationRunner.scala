package org.akka.templates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.{HealthCheckEndpoint, UserEndpoint}

import org.akka.templates.model.UserRepository

import akka.http.scaladsl.server.Directives._

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with UserEndpoint with HealthCheckEndpoint {
  import org.akka.templates.response.rejection._
  import org.akka.templates.db._

  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher
  override val userRepository: UserRepository = new UserRepository()

  Http().bindAndHandle(apiRoute ~ healthCheckApiRoute, "localhost", 8080)
}
