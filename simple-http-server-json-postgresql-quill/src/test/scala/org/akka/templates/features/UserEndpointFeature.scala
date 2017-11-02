package org.akka.templates.features

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route.seal
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.akka.templates.endpoints.UserEndpoint
import org.akka.templates.logging.loggableRoute
import org.akka.templates.model.UserRepository
import org.akka.templates.response.rejection._
import org.scalatest.{BeforeAndAfterAll, FeatureSpec, Matchers}

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class UserEndpointFeature
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with BeforeAndAfterAll
    with UserEndpoint {

  import org.akka.templates.db._

  override protected def beforeAll(): Unit = {
    EmbeddedPostgreSQL.start
  }

  override protected def afterAll(): Unit = {
    ctx.close()
    EmbeddedPostgreSQL.stop
  }

  override val userRepository: UserRepository = new UserRepository()

  feature("user api") {
    val userRoute = loggableRoute(seal(apiRoute))
    
    scenario("successful get") {
      val user =
        """{
         "username": "gabfssilva",
         "age": 24
        }"""

      Post("/api/users", HttpEntity(ContentTypes.`application/json`, user)) ~> userRoute ~> check {
        status shouldBe StatusCodes.Created

        Get(header("Location").map(_.value()).orNull) ~> userRoute ~> check {
          status shouldBe StatusCodes.OK
        }
      }
    }

    scenario("unprocessable entity") {
      Post(s"/api/users", HttpEntity(ContentTypes.`application/json`, "{}")) ~> userRoute ~> check {
        status shouldBe StatusCodes.UnprocessableEntity
      }
    }
  }
}