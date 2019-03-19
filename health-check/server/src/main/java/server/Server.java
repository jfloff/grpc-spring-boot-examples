package server;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import grpc.health.v1.HealthCheckGrpc;
import grpc.health.v1.HealthCheckOuterClass;
import io.grpc.stub.StreamObserver;
import java.util.Random;

@Slf4j
@GRpcService
public class Server extends HealthCheckGrpc.HealthCheckImplBase {

    @Override
    public void check(HealthCheckOuterClass.Service request, StreamObserver<HealthCheckOuterClass.Status> responseObserver) {
        HealthCheckOuterClass.Status.ServingStatus status = HealthCheckOuterClass.Status.ServingStatus.forNumber(new Random().nextInt(3));
        final HealthCheckOuterClass.Status.Builder replyBuilder = HealthCheckOuterClass.Status.newBuilder().setStatus(status);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}