package com.demo.grpcservice.services;

import com.demo.number.NumberRequest;
import com.demo.number.NumberResponse;
import com.demo.number.NumberServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class NumberService extends NumberServiceGrpc.NumberServiceImplBase {
  @Override
  public void getSquare(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {
    // super.getSquare(request, responseObserver);
    var number = request.getNumber();
    var response = NumberResponse.newBuilder().setNumber(number).setSquare(number * number).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<NumberRequest> getSquareStream(
      StreamObserver<NumberResponse> responseObserver) {

    StreamObserver<NumberRequest> requestStreamObserver =
        new StreamObserver<NumberRequest>() {
          @Override
          public void onNext(NumberRequest request) {
            var number = request.getNumber();
            var response =
                NumberResponse.newBuilder().setNumber(number).setSquare(number * number).build();
            responseObserver.onNext(response);
          }

          @Override
          public void onError(Throwable t) {}

          @Override
          public void onCompleted() {
            responseObserver.onCompleted();
          }
        };

    return requestStreamObserver;
  }
}
