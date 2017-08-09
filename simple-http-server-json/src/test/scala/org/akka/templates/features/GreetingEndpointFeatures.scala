package org.akka.templates.features

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.akka.templates.endpoints.GreetingEndpoint
import org.scalatest.{FeatureSpec, Matchers}

/**
  * @author Gabriel Francisco <peo_gfsilva@uolinc.com>
  */
class GreetingEndpointFeatures
  extends FeatureSpec
    with Matchers
    with ScalatestRouteTest
    with GreetingEndpoint {

  feature("Greeting api") {
    scenario("get") {


    }
  }
}