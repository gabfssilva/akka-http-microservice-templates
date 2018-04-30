package io.github.gabfssilva.modules

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.softwaremill.macwire._
import io.github.gabfssilva.endpoints.{Endpoints, GreetingEndpoint, HealthCheckEndpoint}

import scala.concurrent.ExecutionContext

class AllModules extends EndpointModules

trait EndpointModules extends AkkaModules {
  lazy val healthCheckEndpoint = wire[HealthCheckEndpoint]
  lazy val greetingEndpoint = wire[GreetingEndpoint]

  lazy val endpoints = wire[Endpoints]
}

trait AkkaModules {
  implicit lazy val system = ActorSystem("simpleHttpServerJson")
  implicit lazy val materializer = ActorMaterializer()
  implicit lazy val executor: ExecutionContext = system.dispatcher
}