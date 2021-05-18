package com.demo.gateway.services;

import com.demo.gateway.models.SquareResponse;
import com.demo.number.NumberRequest;
import com.demo.number.NumberResponse;
import com.demo.number.NumberServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@Service
public class RpcServiceImpl {
  //private static final String BASE_URL = "207.148.98.248";
  private static final String BASE_URL = "localhost";
  private static final Integer PORT = 9090;
  private ManagedChannel channel =
      ManagedChannelBuilder.forAddress(BASE_URL, PORT).usePlaintext().build();

  public List<SquareResponse> getData(Long limit) {
    NumberServiceGrpc.NumberServiceBlockingStub client = NumberServiceGrpc.newBlockingStub(channel);
    List<SquareResponse> responseList = new ArrayList<>();
    LongStream.range(1, limit + 1)
        .forEach(
            number -> {
              NumberRequest request = NumberRequest.newBuilder().setNumber(number).build();
              NumberResponse response = client.getSquare(request);
              SquareResponse squareResponse = new SquareResponse();
              squareResponse.setNumber(response.getNumber());
              squareResponse.setSquare(response.getSquare());
              responseList.add(squareResponse);
            });

    return responseList;
  }

  public List<SquareResponse> getDataStream(Long limit) throws InterruptedException {
    NumberServiceGrpc.NumberServiceStub client = NumberServiceGrpc.newStub(channel);
    List<SquareResponse> responseList = new ArrayList<>();

    CountDownLatch latch = new CountDownLatch(1);
    System.out.println("Request Came222..");

    StreamObserver<NumberRequest> requestObserver =
        client.getSquareStream(
            new StreamObserver<NumberResponse>() {
              @Override
              public void onNext(NumberResponse value) {
                SquareResponse squareResponse = new SquareResponse();
                squareResponse.setNumber(value.getNumber());
                squareResponse.setSquare(value.getSquare());
                responseList.add(squareResponse);
              }

              @Override
              public void onError(Throwable t) {
                latch.countDown();
              }

              @Override
              public void onCompleted() {
                latch.countDown();
              }
            });

    LongStream.range(1, limit + 1)
        .forEach(
            number -> {
              requestObserver.onNext(NumberRequest.newBuilder().setNumber(number).build());
            });

    requestObserver.onCompleted();

    latch.await(10, TimeUnit.SECONDS);
    return responseList;
  }
}
