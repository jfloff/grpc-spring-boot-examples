package ping;

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
public class Ping extends EchoGrpc.EchoImplBase {

    private final ManagedChannel pongChannel;
    private final EchoGrpc.EchoBlockingStub stub;
    private final ReentrantLock lock = new ReentrantLock();

    public Ping() {
        super();
        // we should get this info from a service like eureka
        this.pongChannel = ManagedChannelBuilder.forAddress("pong", 6565)
                                            // Channels are secure by default (via SSL/TLS).
                                            // For the example we disable TLS to avoid needing certificates.
                                            .usePlaintext(true)
                                            .build();
        this.stub = EchoGrpc.newBlockingStub(pongChannel);
    }

    // this simulates a new ping sending a message
    // realistic these clients come all the time
    @EventListener(ApplicationReadyEvent.class)
    public void startup() {
        // send first ping
        pingEcho();
    }

    @Override
    public void echo(EchoOuterClass.Message request, StreamObserver<EchoOuterClass.Message> responseObserver){
        // receives message and prints
        log.info(request.getMessage());
        responseObserver.onNext(pingMessage());
        responseObserver.onCompleted();
        // and then makes a new request
        pingEcho();
    }

    // builds ping message used for both answers to GRPC and new communications
    private EchoOuterClass.Message pingMessage() {
        return EchoOuterClass.Message.newBuilder().setMessage("PING").build();
    }

    private void pingEcho() {
        lock.lock();
        try {
            // this should be either replaced by a health-checking or
            // even better a eureka/zookeeper service discovery system
            while(true){
                try {
                    EchoOuterClass.Message echo = stub.echo(pingMessage());
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