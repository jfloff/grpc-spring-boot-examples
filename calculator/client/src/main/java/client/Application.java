package client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import org.lognet.springboot.grpc.GRpcService;
import io.grpc.stub.StreamObserver;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    //     return application.sources(Application.class);
    // }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class,args);
    }
}