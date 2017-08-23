package org.akka.templates.features

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.akka.templates.endpoints.UserEndpoint
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

  override protected def beforeAll(): Unit = {
    EmbeddedMongoDB.startMongoDB
  }

  override protected def afterAll(): Unit = {
    EmbeddedMongoDB.stopMongoDB
  }

  import org.akka.templates.db._

  override lazy val userRepository: UserRepository = new UserRepository()

  feature("user api") {
    scenario("successful get") {
      val user =
        """{
         "username": "gabfssilva",
         "age": 24
        }"""

      Post("/api/users", user) ~> Route.seal(apiRoute) ~> check {
        status shouldBe StatusCodes.Created

        Get(header("Location").map(_.value()).orNull) ~> Route.seal(apiRoute) ~> check {
          status shouldBe StatusCodes.OK
        }
      }
    }

    scenario("unprocessable entity") {
      Post(s"/api/users", "{}") ~> Route.seal(apiRoute) ~> check {
        status shouldBe StatusCodes.UnprocessableEntity
      }
    }

    //since the id is not a hex string
    scenario("unprocessable entity on get") {
      Get(s"/api/users/1") ~> Route.seal(apiRoute) ~> check {
        status shouldBe StatusCodes.UnprocessableEntity
      }
    }
  }
}