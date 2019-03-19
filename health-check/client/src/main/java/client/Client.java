package client;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import grpc.health.v1.HealthCheckGrpc;
import grpc.health.v1.HealthCheckOuterClass;


@Slf4j
@Component
public class Client {
    private final ManagedChannel channel;
    private final HealthCheckGrpc.HealthCheckBlockingStub stub;
    private String name;

    public Client() {
        // we should get this info from a service like eureka
        this.channel = ManagedChannelBuilder.forAddress("server", 6565)
                                            // Channels are secure by default (via SSL/TLS).
                                            // For the example we disable TLS to avoid needing certificates.
                                            .usePlaintext(true)
                                            .build();
        this.stub = HealthCheckGrpc.newBlockingStub(channel);
    }

    // this simulates a new client sending a message
    // realistic these clients come all the time
    @EventListener(ApplicationReadyEvent.class)
    public void startup() throws InterruptedException {
        this.healthcheck();
        System.exit(0);
    }

    public void healthcheck() throws InterruptedException {
        log.info("Health checking server ...");

        // build message
        HealthCheckOuterClass.Service service = HealthCheckOuterClass.Service.newBuilder().setService("server").build();

        // this should be either replaced by a health-checking or
        // even better a eureka/zookeeper service discovery system
        while(true){
            try {
                HealthCheckOuterClass.Status status = stub.check(service);
                log.info("Server status: " + status.getStatus());
                Thread.sleep(5000);
            } catch (StatusRuntimeException e) {
                log.info("ERROR ON GREET: " + e.getMessage());
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                return;
            }
        }
    }
}