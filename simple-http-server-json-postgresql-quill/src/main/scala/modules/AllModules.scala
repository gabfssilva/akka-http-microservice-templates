package modules

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import endpoints.{Endpoints, HealthCheckEndpoint, UserEndpoint}
import models.repository.UserRepository
import quill.DBContext

import scala.concurrent.ExecutionContext

class AllModules extends EndpointModule

trait EndpointModule extends AkkaModule with RepositoryModule {
  lazy val healthCheckEndpoint = wire[HealthCheckEndpoint]
  lazy val userEndpoint = wire[UserEndpoint]

  lazy val endpoints = wire[Endpoints]
}

trait QuillModule extends ConfigModule {
  lazy val ctx = wire[DBContext]
}

trait RepositoryModule extends AkkaModule with QuillModule {
  lazy val userRepository = wire[UserRepository]
}

trait AkkaModule {
  implicit lazy val system = ActorSystem("simpleHttpServerJson")
  implicit lazy val materializer = ActorMaterializer()
  implicit lazy val executor: ExecutionContext = system.dispatcher
}

trait ConfigModule {
  lazy val config = ConfigFactory.load()
}