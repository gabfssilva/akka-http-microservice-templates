package features

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import modules.AllModulesTest
import org.scalatest.{BeforeAndAfterAll, FeatureSpec, Matchers}

class UserEndpointFeature
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with BeforeAndAfterAll {

  val modules = new AllModulesTest

  val routes = modules.endpoints.routes

  val httpEntity: (String) => HttpEntity.Strict = (str: String) => HttpEntity(ContentTypes.`application/json`, str)

  feature("user api") {
    scenario("success creation") {
      val validUser =
        """
          {
            "username": "gabfssilva",
            "age": 24
          }
        """

      Post(s"/api/users", httpEntity(validUser)) ~> routes ~> check {
        status shouldBe StatusCodes.Created
      }
    }

    scenario("success get after success creation") {
      val validUser =
        """
          {
            "username": "gabfssilva",
            "age": 24
          }
        """

      Post(s"/api/users", httpEntity(validUser)) ~> routes ~> check {
        status shouldBe StatusCodes.Created

        Get(header("Location").orNull.value()) ~> routes ~> check {
          status shouldBe StatusCodes.OK
        }
      }
    }


    scenario("invalid id on get") {
      Get(s"/api/users/1") ~> routes ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

    scenario("no body") {
      Post(s"/api/users", httpEntity("{}")) ~> routes ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

    scenario("body without age") {
      val invalidUser =
        """
        {
          "username": "gabfssilva"
        }
        """

      Post(s"/api/users", httpEntity(invalidUser)) ~> routes ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }

    scenario("body without username") {
      val invalidUser =
        """
        {
          "age": 24
        }
        """

      Post(s"/api/users", httpEntity(invalidUser)) ~> routes ~> check {
        status shouldBe StatusCodes.BadRequest
      }
    }
  }
}
