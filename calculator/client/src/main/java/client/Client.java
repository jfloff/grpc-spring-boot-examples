package client;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import io.grpc.stub.StreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import calculator.grpc.CalculatorGrpc;
import calculator.grpc.CalculatorOuterClass;


@Slf4j
@Component
public class Client {
    private final ManagedChannel channel;
    private final CalculatorGrpc.CalculatorBlockingStub stub;
    private String calculation;

    public Client() {
        this.calculation = System.getenv("CALCULATION");
        this.channel = ManagedChannelBuilder.forAddress("calculator", 6565)
                                            // Channels are secure by default (via SSL/TLS).
                                            // For the example we disable TLS to avoid needing certificates.
                                            .usePlaintext(true)
                                            .build();
        this.stub = CalculatorGrpc.newBlockingStub(channel);
    }

    // this simulates a new client sending a message
    // realistic these clients come all the time
    @EventListener(ApplicationReadyEvent.class)
    public void startup() throws InterruptedException {
        this.math(this.calculation);
        System.exit(0);
    }

    private CalculatorOuterClass.Math.OperationType stringToOperationType(String s) {
        switch (s) {
            case "+":
                return CalculatorOuterClass.Math.OperationType.ADD;
            case "-":
                return CalculatorOuterClass.Math.OperationType.SUBTRACT;
            case "*":
                return CalculatorOuterClass.Math.OperationType.MULTIPLY;
            case "/":
                return CalculatorOuterClass.Math.OperationType.DIVIDE;
            default:
                System.exit(-1);
                return null;
        }
    }

    public void math(String calculation) throws InterruptedException {
        String[] tokens = calculation.split("((?<=\\+)|(?=\\+))|((?<=-)|(?=-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");

        CalculatorOuterClass.Math math = CalculatorOuterClass.Math.newBuilder()
            .setNumber1(Integer.parseInt(tokens[0]))
            .setNumber2(Integer.parseInt(tokens[2]))
            .setOperation(stringToOperationType(tokens[1]))
            .build();

        // this should be either replaced by a health-checking or
        // even better a eureka/zookeeper service discovery system
        while(true){
            try {
                CalculatorOuterClass.Result result = stub.calculate(math);
                log.info(calculation + " = " + result.getResult());
                return;
            } catch (StatusRuntimeException e) {
                log.info("[ERROR] " + e.getMessage());
                Thread.sleep(1000);
            }
        }
    }
}