package org.akka.templates.response

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object Message {
  val INVALID_PARAMETER = "invalid parameter"
}

class Message(message: String, messageType: String)

case class InvalidParameterMessage(path: String, message: String) extends Message(message, Message.INVALID_PARAMETER)

case class DefaultMessage(message: String, messageType: String = null) extends Message(message, messageType)

case class Envelop[T >: AnyRef](data: T = None, messages: Set[_ <: Message] = Set.empty)