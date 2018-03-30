# gRPC Spring Boot Examples

Collection of working examples of [gRPC](http://www.grpc.io)-based applications running on top of [Spring Boot](https://projects.spring.io/spring-boot/). We used the [LogNet/grpc-spring-boot-starter](https://github.com/LogNet/grpc-spring-boot-starter) gradle plugin, and ported available examples to work OOTB.

Although the provided examples have unit tests that work as clients, we adopt a more in-action example and add a client service. This is useful when you want to bootstrap microservices. We also setup a Docker-ready deployment.

**Feel free to submit a PR for other examples or improvements existing ones!**

## Run
Running examples is easy peasy lemon squeezy! Just go into one of the examples subfolders, and **run `docker-compose up`**! That's it!


## Examples
* **`greeter`**: client sends a name for the server, and the server replies with a greeting. Client name is passed as an env variable in the `docker-compose.yml`. Based on [LogNet/grpc-spring-boot-starter examples](https://github.com/LogNet/grpc-spring-boot-starter/tree/master/grpc-spring-boot-starter-demo).

* **`greeter-interceptors`**: Same as greeter but we add an interceptor to the server(`NonBeanInterceptor.java`) even though server is not a `@Bean`. Based on [LogNet/grpc-spring-boot-starter examples](https://github.com/LogNet/grpc-spring-boot-starter/tree/master/grpc-spring-boot-starter-demo).

* **`greeter-bean-interceptors`**: Same as greeter but we add an interceptor to the server(`LogInterceptor.java`). Based on [LogNet/grpc-spring-boot-starter examples](https://github.com/LogNet/grpc-spring-boot-starter/tree/master/grpc-spring-boot-starter-demo).

* **`calculator`**: Standard calculator, we parse the calculation form an env variable on the client, and then we send the parcels over to the server. Based on [LogNet/grpc-spring-boot-starter examples](https://github.com/LogNet/grpc-spring-boot-starter/tree/master/grpc-spring-boot-starter-demo).

* **`health-check`**: implementation of gRPC health checking protocol. Each 5 seconds the server randomly answers with one of the possible status. Proto definition at [oficial grpc documentation](https://github.com/grpc/grpc/blob/master/doc/health-checking.md).

## Requirements

Our Docker images are based on [jfloff/docker-thrike](https://github.com/jfloff/docker-thrike) which already everything we need set up, namely: Tomcat, Gradle, Protobuf and gRPC. If you want to bypass Docker and deploy on our own machine, check the [Dockerfile at jfloff/docker-thrike](https://github.com/jfloff/docker-thrike/blob/master/8.5/Dockerfile) for some hints on how to setup your system.


## More infomation
Please refer to the following links for more information:
* [LogNet/grpc-spring-boot-starter](https://github.com/LogNet/grpc-spring-boot-starter)
* [grpc/grpc-java](https://github.com/grpc/grpc-java)
* [google/protobuf-gradle-plugin](https://github.com/google/protobuf-gradle-plugin)
* [gRPC](www.grpc.io) and its [java tutorial](http://www.grpc.io/docs/tutorials/basic/java.html)
* [google/protobuf](https://github.com/google/protobuf)


## License
The code in this repository, unless otherwise noted, is MIT licensed. See the LICENSE file in this repository.