package org.akka.templates

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.akka.templates.endpoints.UserEndpoint
import org.akka.templates.model.UserRepository

import scala.concurrent.ExecutionContextExecutor

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object ApplicationRunner extends App with UserEndpoint {
  import akka.http.scaladsl.Http
  import org.akka.templates.db._

  override implicit val system = ActorSystem("simpleHttpServerJson")
  override implicit val materializer = ActorMaterializer()
  override implicit def executor: ExecutionContextExecutor = system.dispatcher
  override val userRepository: UserRepository = new UserRepository()

  Http().bindAndHandle(apiRoute, "localhost", 8080)
}
