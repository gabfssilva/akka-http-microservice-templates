package org.akka.templates

import io.getquill.{PostgresAsyncContext, SnakeCase}


/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object db {
  type DBContext = PostgresAsyncContext[SnakeCase]

  implicit lazy val ctx = new DBContext("ctx")
}
