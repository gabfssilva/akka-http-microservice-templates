# simple-http-server-json-postgresql-quill

This example shows a simple microservice written in Scala using:

- [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/), to expose the http services
- [Jackson](http://wiki.fasterxml.com/JacksonHome) and [Jackson Module Scala](https://github.com/FasterXML/jackson-module-scala), to parse json into scala objects and vice versa
- [Quill](http://getquill.io) and [quill-async-postgres](http://getquill.io/#contexts-sql-contexts-quill-async-postgres) as a DSL layer to access PostgreSQL reactively
- [Accord](http://wix.github.io/accord/), for model validation
- [Embedded PostgreSQL Server](https://github.com/yandex-qatools/postgresql-embedded), for testing