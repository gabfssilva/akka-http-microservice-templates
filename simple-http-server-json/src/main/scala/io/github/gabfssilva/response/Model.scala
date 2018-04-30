package io.github.gabfssilva.response

import io.circe._
import io.circe.syntax._

case class Response(message: String)

object Response {
  implicit val encoder: Encoder[Response] = (response: Response) => Json.obj("message" -> response.message.asJson)
}

case class Greeting(greeting: String, name: String) {
  require(greeting != "", "greeting cannot be empty")
  require(name != "", "name cannot be empty")

  def greet = s"$greeting, $name!"
}