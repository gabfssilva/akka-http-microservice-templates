package org.akka.templates

import java.lang.System.currentTimeMillis

import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.settings.RoutingSettings
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import akka.stream.Materializer

import scala.concurrent.ExecutionContext

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object logging {
  def logRequestAndResponse(loggingAdapter: LoggingAdapter, before: Long)(req: HttpRequest)(res: Any): Unit = {
    val entry = res match {
      case Complete(resp) =>
        val message = s"{path=${req.uri}, method=${req.method.value}, status=${resp.status.intValue()}, elapsedTime=${currentTimeMillis() - before}"
        LogEntry(message, Logging.InfoLevel)
      case other => LogEntry(other, Logging.InfoLevel)
    }

    entry.logTo(loggingAdapter)
  }

  def cloggableRoute(route: Route)(implicit m: Materializer,
                                  ex: ExecutionContext,
                                  routingSettings: RoutingSettings): Route = {
    DebuggingDirectives.logRequestResult(LoggingMagnet(log => {
      val requestTimestamp = currentTimeMillis()
      logRequestAndResponse(log, requestTimestamp)
    }))(route)
  }
}
