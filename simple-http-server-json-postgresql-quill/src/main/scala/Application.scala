import akka.http.scaladsl.Http
import modules.AllModules

import scala.util.{Failure, Success}

object Application extends App {
  val modules = new AllModules

  import modules._

  Http().bindAndHandle(modules.endpoints.routes, "0.0.0.0", 8080).onComplete {
    case Success(b) => system.log.info(s"application is up and running at ${b.localAddress.getHostName}:${b.localAddress.getPort}")
    case Failure(e) => system.log.error(s"could not start application: {}", e.getMessage)
  }
}