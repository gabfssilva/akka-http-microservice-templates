package org.akka.templates.endpoints

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import org.akka.templates.model.{User, UserRepository}
import org.akka.templates.response._
import org.akka.templates.validators._

import scala.concurrent.ExecutionContextExecutor

import org.akka.templates.json._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
trait UserEndpoint {
  implicit def executor: ExecutionContextExecutor

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val loggedRequest = logRequestResult("users", Logging.InfoLevel)

  val userRepository: UserRepository

  val apiRoute: Route = {
    (pathPrefix("api" / "users") & loggedRequest) {
      (get & path(LongNumber)) { id =>
        complete {
          userRepository
            .findById(id)
            .map {
              case user: Some[User] => ok(Envelop(user))
              case None => notFound(Envelop(messages = Set(DefaultMessage(s"user with id=$id not found"))))
            }
        }
      } ~ (post & entity(as[User])) { user =>
        validateUser(user) {
          complete {
            userRepository
              .save(user)
              .map { id => created(s"/api/users/$id") }
          }
        }
      }
    }
  }
}
