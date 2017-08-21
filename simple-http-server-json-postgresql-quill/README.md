# simple-http-server-json-postgresql-quill

This example shows a simple microservice written in Scala using:

- [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/), to expose the http services
- [Quill](http://getquill.io), as a DSL layer to access the database
- [Accord](http://wix.github.io/accord/), for model validation
- [Embedded PostgreSQL Server](https://github.com/yandex-qatools/postgresql-embedded), for testing