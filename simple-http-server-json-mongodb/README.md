# simple-http-server-json-mongodb

This example shows a simple microservice written in Scala using:

- [Akka HTTP](http://doc.akka.io/docs/akka-http/current/scala/http/), to expose the http services
- [MongoDB Scala Driver](https://mongodb.github.io/mongo-scala-driver/), to access MongoDB reactively
- [Circe](https://circe.github.io/circe/), to parse json into scala objects and vice versa
- [MacWire](https://github.com/adamw/macwire), a simple compile time dependency injection library
- [Fongo](https://github.com/fakemongo/fongo), a fake in-memory MongoDB for testing purposes

## Building your project

If you do not have SBT installed:

### Installing SBT

#### deb
``` 
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt
```

#### rpm
```
curl https://bintray.com/sbt/rpm/rpm > bintray-sbt-rpm.repo
sudo mv bintray-sbt-rpm.repo /etc/yum.repos.d/
sudo yum install sbt
```

#### macOS (using brew)

```
brew install sbt@1
```

### Building

```
sbt clean compile
```

### Running the tests
```
sbt test
```

### Running the application at local machine

If you want to run the app, you must use the SBT interactive mode:

Just type "sbt" in the terminal and after the REPL starts, run: '~re-start'

```
user@machine:/workspace/simple-http-server-json$ sbt
[info] Loading global plugins from /home/user/.sbt/0.13/plugins
[info] Updating {file:/home/user/.sbt/0.13/plugins/}global-plugins...
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Loading project definition from /workspace/simple-http-server-json/project
[info] Set current project to simple-http-server-json (in build file:/workspaces/simple-http-server-json/)
> ~re-start
```

Using the '~re-start' task, the hot-deployment will automatically be activated. ;)

#### Debbuging

After you run the task '~re-start', just attach a remote debug on localhost:5005.
