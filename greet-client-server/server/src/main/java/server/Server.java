package server;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import greet.grpc.GreeterGrpc;
import greet.grpc.GreeterOuterClass;
import io.grpc.stub.StreamObserver;

@Slf4j
@GRpcService
public class Server extends GreeterGrpc.GreeterImplBase {

    @Override
    public void greet(GreeterOuterClass.Name request, StreamObserver<GreeterOuterClass.Greeting> responseObserver) {
        log.info(request.getName() + ": Hello there!");
        String message = "General " + request.getName() + "! You are a bold one...";
        final GreeterOuterClass.Greeting.Builder replyBuilder = GreeterOuterClass.Greeting.newBuilder().setMessage(message);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}