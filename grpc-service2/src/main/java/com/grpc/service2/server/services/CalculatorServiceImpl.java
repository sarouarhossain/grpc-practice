package com.grpc.service2.server.services;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
  private TestService testService;

  public CalculatorServiceImpl(TestService testService) {
    super();
    this.testService = testService;
  }

  @Override
  public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
    // super.sum(request, responseObserver);
    System.out.println("In Calculator Service: " + Thread.currentThread().getName());
    System.out.println("Object ID of Calc Service: " + this.hashCode());

    var res = request.getFirstNumber() + request.getSecondNumber();
    System.out.println(
        "First Number: "
            + request.getFirstNumber()
            + ",  Second Number: "
            + request.getSecondNumber());
    SumResponse response = SumResponse.newBuilder().setResult(res).build();
    System.out.println("Returning Response: " + response);
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
