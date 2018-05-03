package modules

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.softwaremill.macwire._
import com.typesafe.config.{Config, ConfigFactory}
import endpoints.{Endpoints, HealthCheckEndpoint, UserEndpoint}
import models.repository.UserRepository
import scalikejdbc.{AutoSession, ConnectionPool, ConnectionPoolSettings, DBSession}

import scala.concurrent.ExecutionContext

class AllModules extends EndpointModule

trait EndpointModule extends AkkaModule with RepositoryModule {
  lazy val healthCheckEndpoint = wire[HealthCheckEndpoint]
  lazy val userEndpoint = wire[UserEndpoint]

  lazy val endpoints = wire[Endpoints]
}

trait ScalikeJDBCModule extends ConfigModule {
  Class.forName(config.getString("jdbc.driver"))

  val connectionPoolSettings = ConnectionPoolSettings(
    initialSize = 1,
    maxSize = config.getInt("jdbc.maxConnections")
  )

  ConnectionPool.singleton(config.getString("jdbc.url"), config.getString("jdbc.username"), config.getString("jdbc.password"), connectionPoolSettings)

  lazy val session: DBSession = AutoSession
  lazy val databaseExecutorContext: ExecutionContext =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(config.getInt("jdbc.maxConnections")))
}

trait RepositoryModule extends ScalikeJDBCModule {
  lazy val userRepository = wire[UserRepository]
}

trait AkkaModule {
  implicit lazy val system = ActorSystem("simpleHttpServerJson")
  implicit lazy val materializer = ActorMaterializer()
  implicit lazy val executor: ExecutionContext = system.dispatcher
}

trait ConfigModule {
  lazy val config: Config = ConfigFactory.load()
}