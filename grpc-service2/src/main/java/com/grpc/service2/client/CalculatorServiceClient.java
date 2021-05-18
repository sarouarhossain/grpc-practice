package com.grpc.service2.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorServiceClient {
  public static void main(String[] args) {
    //
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    CalculatorServiceGrpc.CalculatorServiceBlockingStub stub =
        CalculatorServiceGrpc.newBlockingStub(channel);

    SumRequest request = SumRequest.newBuilder().setFirstNumber(100).setSecondNumber(9921).build();

    SumResponse res = stub.sum(request);

    System.out.println(res);
    channel.shutdown();
  }
}
