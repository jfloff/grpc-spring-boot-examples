# gRPC Spring Boot Examples

Collection of working examples of [gRPC](http://www.grpc.io) application running on top of [Spring Boot](https://projects.spring.io/spring-boot/). We used the [LogNet/grpc-spring-boot-starter](https://github.com/LogNet/grpc-spring-boot-starter), and ported available examples to work OOTB. We added a client service to all the examples. We also setup a Docker-ready deployment. Running examples is now easy peasy lemon squeezy!

**Feel free to submit a PR for other examples or improvements existing ones!**

## Run
Just go into one of the examples subfolders, and run `docker-compose up`! That's it!


## Examples
* `greet-client-server`: client sends a name for the server, and the server replies with a greeting. Client name is passed as an env variable in the `docker-compose.yml`.


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