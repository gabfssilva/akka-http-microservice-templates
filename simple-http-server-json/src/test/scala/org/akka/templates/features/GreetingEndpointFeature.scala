package org.akka.templates.features

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.akka.templates.endpoints.GreetingEndpoint
import org.akka.templates.logging.loggableRoute
import org.scalatest.{FeatureSpec, Matchers}
import org.akka.templates.response.rejection._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class GreetingEndpointFeature
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with GreetingEndpoint {

  feature("greeting api") {
    val route = loggableRoute(Route.seal(apiRoute))

    scenario("successful get") {
      Get(s"/api/greetings?name=gabriel&greeting=hello") ~> route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include (s"hello, gabriel")
      }
    }

    scenario("unprocessable entity get") {
      Get(s"/api/greetings?name=gabriel&greeting=") ~> route ~> check {
        status shouldBe StatusCodes.UnprocessableEntity
      }
    }
  }
}