package org.akka.templates

import scalikejdbc._

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object db {
  Class.forName("org.postgresql.Driver")

  val connectionPoolSettings = ConnectionPoolSettings(
    initialSize = 0,
    maxSize = 10
  )

  ConnectionPool.singleton("jdbc:postgresql://localhost:5432/users", "user", "password", connectionPoolSettings)

  implicit val session = AutoSession
}
