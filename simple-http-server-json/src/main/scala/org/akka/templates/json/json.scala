package org.akka.templates

import akka.http.scaladsl.marshalling.Marshaller.withFixedContentType
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest}
import akka.http.scaladsl.unmarshalling.{FromRequestUnmarshaller, Unmarshal, Unmarshaller}
import akka.stream.Materializer
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.concurrent.ExecutionContext

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object json {
  val objectMapper = new ObjectMapper()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .setSerializationInclusion(Include.NON_NULL)
    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
    .registerModule(DefaultScalaModule)

  implicit class ObjAsJsonUsingJackson(obj: Any) {
    def asJson: String = objectMapper.writeValueAsString(obj)
  }

  implicit class StringJsonAsCaseClass(json: String) {
    def asObject[T](implicit m: Manifest[T]): T = objectMapper.readValue(json, m.runtimeClass).asInstanceOf[T]
  }

  implicit def jsonMarshaller[T]: ToEntityMarshaller[T] =
    withFixedContentType(ContentTypes.`application/json`) { any =>
      HttpEntity(ContentTypes.`application/json`, any.asJson)
    }

  implicit def jsonUnmarshaller[T](implicit m: Manifest[T], materializer: Materializer): FromRequestUnmarshaller[T] =
    Unmarshaller[HttpRequest, T] {
      implicit ec: ExecutionContext => r => Unmarshal(r.entity).to[String].map(_.asObject[T])
    }
}
