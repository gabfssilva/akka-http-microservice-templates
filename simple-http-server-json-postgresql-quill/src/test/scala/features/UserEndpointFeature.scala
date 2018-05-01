package features

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import modules.AllModules
import org.scalatest.{BeforeAndAfterAll, FeatureSpec, Matchers}
import utils.EmbeddedPostgreSQL

class UserEndpointFeature
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with BeforeAndAfterAll {

  val modules = new AllModules

  override def beforeAll(): Unit = {
    EmbeddedPostgreSQL.start
  }

  override def afterAll(): Unit = {
    modules.ctx.close()
    EmbeddedPostgreSQL.stop
  }

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
      Get(s"/api/users/asd") ~> routes ~> check {
        status shouldBe StatusCodes.NotFound
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
