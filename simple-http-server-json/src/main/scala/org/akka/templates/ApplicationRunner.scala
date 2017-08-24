package org.akka.templates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import org.akka.templates.endpoints.GreetingEndpoint

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with GreetingEndpoint {
  import org.akka.templates.response.rejection._

  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher

  Http().bindAndHandle(apiRoute, "localhost", 8080)
}
