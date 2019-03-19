package pong;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import echo.grpc.EchoGrpc;
import echo.grpc.EchoOuterClass;
import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.locks.ReentrantLock;


@Slf4j
@GRpcService
public class Pong extends EchoGrpc.EchoImplBase {

    private final ManagedChannel pingChannel;
    private final EchoGrpc.EchoBlockingStub stub;
    private final ReentrantLock lock = new ReentrantLock();

    public Pong() {
        super();
        // we should get this info from a service like eureka
        this.pingChannel = ManagedChannelBuilder.forAddress("ping", 6565)
                                            // Channels are secure by default (via SSL/TLS).
                                            // For the example we disable TLS to avoid needing certificates.
                                            .usePlaintext(true)
                                            .build();
        this.stub = EchoGrpc.newBlockingStub(pingChannel);
    }

    @Override
    public void echo(EchoOuterClass.Message request, StreamObserver<EchoOuterClass.Message> responseObserver){
        // receives message and prints
        log.info(request.getMessage());
        responseObserver.onNext(pongMessage());
        responseObserver.onCompleted();
        // and then makes a new request
        pongEcho();
    }

    // builds pong message used for both answers to GRPC and new communications
    private EchoOuterClass.Message pongMessage() {
        return EchoOuterClass.Message.newBuilder().setMessage("PONG").build();
    }

    private void pongEcho() {
        lock.lock();
        try {
            // this should be either replaced by a health-checking or
            // even better a eureka/zookeeper service discovery system
            while(true){
                try {
                    EchoOuterClass.Message echo = stub.echo(pongMessage());
                    log.info(echo.getMessage());
                    return;
                } catch (StatusRuntimeException e) {
                    log.info("ERROR ON ECHO: " + e.getMessage());
                    coolOff(5);
                } finally {
                    coolOff(2);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void coolOff(int s) {
        // always wait 2 seconds for next message
        log.info("Just hold on " + s + " seconds ...");
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException ie) { }
    }
}