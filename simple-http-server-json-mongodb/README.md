# simple-http-server-json-mongodb

This example shows a simple microservice written in Scala using:

- [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/), to expose the http services
- [Jackson](http://wiki.fasterxml.com/JacksonHome) and [Jackson Module Scala](https://github.com/FasterXML/jackson-module-scala), to parse json into scala objects and vice versa
- [MongoDB Scala Driver](https://mongodb.github.io/mongo-scala-driver/), to access MongoDB reactively
- [Accord](http://wix.github.io/accord/), for model validation
- [Embedded MongoDB](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo), for testing