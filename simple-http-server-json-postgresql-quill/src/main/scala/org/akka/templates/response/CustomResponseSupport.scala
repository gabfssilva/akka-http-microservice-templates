package org.akka.templates.response

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.Location
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import scala.concurrent.{ExecutionContext, Future}
trait CustomResponseSupport extends JacksonSupport {
  def response[T](status: StatusCode = StatusCodes.OK,
                  entity: Option[T] = None,
                  headers: List[HttpHeader] = List.empty)(implicit ec: ExecutionContext): Future[HttpResponse] = {
    entity match {
      case None => Future.successful(HttpResponse(status = status, headers = headers))
      case Some(e) => Marshal(e).to[ResponseEntity].map { e => HttpResponse(status = status, entity = e, headers = headers) }
    }
  }

  def ok[T](obj: T)(implicit ec: ExecutionContext): Future[HttpResponse] = response(entity = Some(obj))
  def created(location: String)(implicit ec: ExecutionContext): Future[HttpResponse] = response(status = StatusCodes.Created, headers = List(Location(location)))
  def unprocessableEntity[T](obj: T)(implicit ec: ExecutionContext): Future[HttpResponse] = response(status = StatusCodes.UnprocessableEntity, entity = Some(obj))
  def notFound[T](obj: T)(implicit ec: ExecutionContext): Future[HttpResponse] = response(status = StatusCodes.NotFound, entity = Some(obj))
}