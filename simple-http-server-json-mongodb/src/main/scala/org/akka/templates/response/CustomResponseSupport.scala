package org.akka.templates.response

import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import org.akka.templates.json._

trait CustomResponseSupport {
  def ok(obj: AnyRef): HttpResponse = {
    HttpResponse(
      status = 200,
      entity = HttpEntity(ContentTypes.`application/json`, obj.asJson)
    )
  }

  def created(location: String): HttpResponse = {
    HttpResponse(
      status = 201,
      headers = List(Location(location))
    )
  }

  def unprocessableEntity(obj: AnyRef): HttpResponse = {
    HttpResponse(
      status = 422,
      entity = HttpEntity(ContentTypes.`application/json`, obj.asJson)
    )
  }

  def notFound(obj: AnyRef): HttpResponse =
    HttpResponse(
      status = 404,
      entity = HttpEntity(ContentTypes.`application/json`, obj.asJson)
    )
}