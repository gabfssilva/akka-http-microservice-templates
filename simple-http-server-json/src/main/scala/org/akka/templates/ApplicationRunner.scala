package org.akka.templates

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.GreetingEndpoint

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with GreetingEndpoint {
  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher

  import org.akka.templates.response.rejection._

  import akka.http.scaladsl.Http

  Http().bindAndHandle(apiRoute, "localhost", 8080)
}
