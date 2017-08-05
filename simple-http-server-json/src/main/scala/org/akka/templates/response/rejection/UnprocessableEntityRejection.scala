package org.akka.templates.response.rejection

import akka.http.scaladsl.server.Rejection
import org.validation.scala.Violation

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
case class UnprocessableEntityRejection(violations: List[Violation]) extends Rejection
