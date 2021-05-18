package com.demo.grpcservice.services;

import com.demo.blog.BlogServiceGrpc;
import com.demo.blog.CreateBlogRequest;
import com.demo.blog.CreateBlogResponse;
import com.demo.grpcservice.models.Blog;
import com.demo.grpcservice.repositories.BlogRepository;
import com.google.gson.Gson;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@GrpcService
public class BlogServiceImpl extends BlogServiceGrpc.BlogServiceImplBase {
  Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);
//  private final BlogRepository blogRepository;
//  private final DateTimeFormatter formatter =
//      DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss Z");
//
//  public BlogServiceImpl(BlogRepository blogRepository) {
//    this.blogRepository = blogRepository;
//  }

  @Override
  public void createBlog(
      CreateBlogRequest request, StreamObserver<CreateBlogResponse> responseObserver) {
    // super.createBlog(request, responseObserver);
    Blog blog = new Blog();
    //blog.setId(request.getBlog().getId());
    blog.setAuthorId(request.getBlog().getAuthorId());
    blog.setTitle(request.getBlog().getTitle());
    blog.setContent(request.getBlog().getContent());
    logger.info("Before Save: " + new Gson().toJson(blog));

//    var saved = blogRepository.save(blog);
//    logger.info("Saved Blog: " + new Gson().toJson(saved));
//    CreateBlogResponse response =
//        CreateBlogResponse.newBuilder()
//            .setBlog(
//                com.demo.blog.Blog.newBuilder()
//                    .setId(blog.getId())
//                    .setAuthorId(blog.getAuthorId())
//                    .setTitle(blog.getTitle())
//                    .setContent(blog.getContent())
//                    .setCreatedAt(saved.getCreatedAt().format(formatter))
//                    .build())
//            .build();
//    logger.info("Saved Blog: " + new Gson().toJson(response));
//
//    responseObserver.onNext(response);
//    responseObserver.onCompleted();
  }
}
