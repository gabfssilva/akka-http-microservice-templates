package org.akka.templates.response.rejection

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Rejection
import com.wix.accord.Violation

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
abstract class HttpRejection(val statusCode: StatusCode) extends Rejection

case class UnprocessableEntityRejection(violations: Set[Violation]) extends HttpRejection(StatusCodes.UnprocessableEntity)
