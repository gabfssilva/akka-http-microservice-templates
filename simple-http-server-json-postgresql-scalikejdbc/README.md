# simple-http-server-json-postgresql-quill

This example shows a simple microservice written in Scala using:

- [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/), to expose the http services
- [Jackson](http://wiki.fasterxml.com/JacksonHome) and [Jackson Module Scala](https://github.com/FasterXML/jackson-module-scala), to parse json into scala objects and vice versa
- [ScalikeJDBC](http://scalikejdbc.org/), to access PostgreSQL using a Scala JDBC wrapper
- [Accord](http://wix.github.io/accord/), for model validation
- [Embedded PostgreSQL Server](https://github.com/yandex-qatools/postgresql-embedded), for testing

### Note on ScalikeJDBC:
ScalikeJDBC, as the name says, uses JDBC behind the scenes, and, as we all know JDBC uses blocking I/O.
However, we can optimize our thread creation and not use the connection pool directly from the global execution context. The main idea is to create an isolated thread pool to handle all blocking jdbc calls. The size of this thread pool should be the same size of the connection pool, so, no connection or thread will be idle for the operations and, since Akka HTTP is fully non-blocking, it also will not create many threads.

If you wish to have a fully non-blocking application, please see [our example using Quill](https://github.com/gabfssilva/akka-http-microservice-templates/tree/master/simple-http-server-json-postgresql-quill).

#### Note on ScalikeJDBC, part 2:
Not many RDBMS drivers provide a fully non-blocking I/O operations, so, we find extremely valid to reuse JDBC libraries to access those RDBMS which do not provide a non-blocking driver.