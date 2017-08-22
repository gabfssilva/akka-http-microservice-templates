package org.akka.templates

import java.util.concurrent.Executors

import scalikejdbc._

import scala.concurrent.ExecutionContext

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
package object db {
  type DatabaseExecutorContext = ExecutionContext

  Class.forName("org.postgresql.Driver")

  val maxPoolSize = 10

  val connectionPoolSettings = ConnectionPoolSettings(
    initialSize = 1,
    maxSize = maxPoolSize
  )

  ConnectionPool.singleton("jdbc:postgresql://localhost:5432/users", "user", "password", connectionPoolSettings)

  implicit val session = AutoSession
  implicit val databaseExecutorContext: DatabaseExecutorContext =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(maxPoolSize))
}
