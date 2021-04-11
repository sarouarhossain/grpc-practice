package com.demo.userservice;

import com.demo.dummy.DummyMessage;
import com.demo.dummy.DummyServiceGrpc;
import com.demo.dummy.voidN;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestDummyService extends DummyServiceGrpc.DummyServiceImplBase {
  @Override
  public void getMessage(voidN request, StreamObserver<DummyMessage> responseObserver) {
    // super.getMessage(request, responseObserver);
    DummyMessage message = DummyMessage.newBuilder().setId(1).setMessage("Hello world").build();
    responseObserver.onNext(message);
    responseObserver.onCompleted();
  }
}
