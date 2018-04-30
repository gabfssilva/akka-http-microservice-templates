name := "simple-http-server-json-mongodb"
organization := "org.akka.templates"
version := "0.0.1"
scalaVersion := "2.12.6"

resolvers += Resolver.jcenterRepo

Revolver.enableDebugging(port = 5005, suspend = false)

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}

val akkaHttp = "10.1.1"
val akka = "2.5.11"
val circe = "0.9.3"
val macwire = "2.3.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttp,
  "com.typesafe.akka" %% "akka-stream" % akka,
  "com.typesafe.akka" %% "akka-slf4j" % akka,

  "de.heikoseeberger" %% "akka-http-circe" % "1.20.1",

  "io.circe" %% "circe-generic" % circe,

  "com.softwaremill.macwire" %% "macros" % macwire,
  "com.softwaremill.macwire" %% "util" % macwire,

  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",

  //test libraries
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.pegdown" % "pegdown" % "1.6.0" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttp % "test",
  "org.mongodb" % "mongo-java-driver" % "3.4.2",
  "com.github.fakemongo" % "fongo" % "2.1.0" % "test"
)

testOptions in Test ++= Seq(
  Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
)