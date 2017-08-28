package org.akka.templates.features

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.akka.templates.endpoints.HealthCheckEndpoint
import org.akka.templates.response.rejection._
import org.scalatest.{FeatureSpec, Matchers}

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
class HealthCheckEndpointFeature
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with HealthCheckEndpoint {

  feature("health check api") {
    scenario("successful get") {
      Get(s"/api/health-check") ~> Route.seal(healthCheckApiRoute) ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] shouldBe "ok"
      }
    }
  }
}