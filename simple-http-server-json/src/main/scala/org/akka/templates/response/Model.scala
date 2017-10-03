package org.akka.templates.response

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
case class Greeting(greeting: Option[String], name: Option[String]) {
  def greet: Option[String] = {
    for {
      g <- greeting
      n <- name
    } yield s"$g, $n!"
  }
}

case class Response(message: Option[String])

object Message {
  val INVALID_PARAMETER = "invalid parameter"
}

class Message(message: String, messageType: String)

case class InvalidParameterMessage(path: String, message: String) extends Message(message, Message.INVALID_PARAMETER)

case class DefaultMessage(message: String, messageType: String) extends Message(message, messageType)

case class Envelop[T >: AnyRef](data: T = None, messages: Set[_ <: Message] = Set.empty)