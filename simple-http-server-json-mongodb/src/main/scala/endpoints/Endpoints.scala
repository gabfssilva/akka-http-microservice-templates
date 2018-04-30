package endpoints

import java.lang.System.currentTimeMillis

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives._
import akka.http.scaladsl.settings.RoutingSettings
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContext

class Endpoints(userEndpoint: UserEndpoint,
                healthCheckEndpoint: HealthCheckEndpoint) {
  def routes(implicit
             sys: ActorSystem,
             mat: ActorMaterializer,
             ec: ExecutionContext) = loggableRoute {
    Route.seal {
      userEndpoint.userRoutes ~ healthCheckEndpoint.healthCheckRoute
    }
  }

  def logRequestAndResponse(loggingAdapter: LoggingAdapter, before: Long)(req: HttpRequest)(res: Any): Unit = {
    val entry = res match {
      case Complete(resp) =>
        val message = s"{path=${req.uri}, method=${req.method.value}, status=${resp.status.intValue()}, elapsedTime=${currentTimeMillis() - before}"
        LogEntry(message, Logging.InfoLevel)
      case other => LogEntry(other, Logging.InfoLevel)
    }

    entry.logTo(loggingAdapter)
  }

  def loggableRoute(route: Route)(implicit m: Materializer,
                                  ex: ExecutionContext,
                                  routingSettings: RoutingSettings): Route = {
    DebuggingDirectives.logRequestResult(LoggingMagnet(log => {
      val requestTimestamp = currentTimeMillis()
      logRequestAndResponse(log, requestTimestamp)
    }))(route)
  }
}
