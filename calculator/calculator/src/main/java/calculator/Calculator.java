package calculator;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.grpc.stub.StreamObserver;

import calculator.grpc.CalculatorGrpc;
import calculator.grpc.CalculatorOuterClass;

@Slf4j
@GRpcService
public class Calculator extends CalculatorGrpc.CalculatorImplBase {

    @Override
    public void calculate(CalculatorOuterClass.Math math, StreamObserver<CalculatorOuterClass.Result> result) {
        CalculatorOuterClass.Result.Builder resultBuilder = CalculatorOuterClass.Result.newBuilder();
        switch (math.getOperation()){
            case ADD:
                resultBuilder.setResult(math.getNumber1()+math.getNumber2());
                break;
            case SUBTRACT:
                resultBuilder.setResult(math.getNumber1()-math.getNumber2());
                break;
            case MULTIPLY:
                resultBuilder.setResult(math.getNumber1()*math.getNumber2());
                break;
            case DIVIDE:
                resultBuilder.setResult(math.getNumber1()/math.getNumber2());
                break;
            case UNRECOGNIZED:
                break;
        }
        result.onNext(resultBuilder.build());
        result.onCompleted();
    }
}
