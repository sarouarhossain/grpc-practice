package com.grpc.service2.server.services;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
  @Override
  public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
    // super.greet(request, responseObserver);
    Greeting greeting = request.getGreeting();
    String result = "Hello " + greeting.getFirstName() + " " + greeting.getLastName();
    GreetResponse response = GreetResponse.newBuilder().setResult(result).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
