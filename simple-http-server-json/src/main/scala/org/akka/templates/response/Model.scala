package org.akka.templates.response

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
case class Greeting(greeting: String, name: String) {
  def greet: String = s"$greeting, $name"
}

case class Response(message: String)

object Message {
  val INVALID_PARAMETER = "invalid parameter"
}

class Message(message: String, messageType: String)

case class InvalidParameterMessage(path: String, message: String) extends Message(message, Message.INVALID_PARAMETER)

case class DefaultMessage(message: String, messageType: String) extends Message(message, messageType)

case class Envelop(greeting: Option[Greeting] = None,
                   messages: List[Message] = List.empty)
