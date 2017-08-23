package org.akka.templates.features

import de.flapdoodle.embed.mongo.{MongodExecutable, MongodProcess, MongodStarter}
import de.flapdoodle.embed.mongo.config.{IMongodConfig, MongodConfigBuilder, Net}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network

/**
  * @author Gabriel Francisco <gabfssilva@gmail.com>
  */
object EmbeddedMongoDB {
  val starter: MongodStarter = MongodStarter.getDefaultInstance

  val bindIp = "localhost"
  val port = 27017
  val mongodConfig: IMongodConfig =
    new MongodConfigBuilder()
      .version(Version.Main.PRODUCTION)
      .net(new Net(bindIp, port, Network.localhostIsIPv6)).build

  val mongodExecutable: MongodExecutable = starter.prepare(mongodConfig)

  def startMongoDB: MongodProcess = {
    mongodExecutable.start
  }

  def stopMongoDB: Unit = {
    mongodExecutable.stop()
  }
}
