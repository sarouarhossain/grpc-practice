package com.grpc.service2.server.services;

import com.proto.blog.BlogServiceGrpc;
import com.proto.blog.CreateBlogRequest;
import com.proto.blog.CreateBlogResponse;
import io.grpc.stub.StreamObserver;

public class BlogServiceImpl extends BlogServiceGrpc.BlogServiceImplBase {
  @Override
  public void create(
      CreateBlogRequest request, StreamObserver<CreateBlogResponse> responseObserver) {
    //    super.create(request, responseObserver);
  }
}
