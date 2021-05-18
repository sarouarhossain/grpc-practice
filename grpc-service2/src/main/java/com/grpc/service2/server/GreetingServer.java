package com.grpc.service2.server;

import com.grpc.service2.server.services.CalculatorServiceImpl;
import com.grpc.service2.server.services.GreetServiceImpl;
import com.grpc.service2.server.services.TestService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
  public static void main(String[] args) throws IOException, InterruptedException {
    //
    System.out.println("In main: " + Thread.currentThread().getName());
    TestService testService = new TestService();
    Server server =
        ServerBuilder.forPort(50051)
            .addService(new GreetServiceImpl())
            .addService(new CalculatorServiceImpl(testService))
            .build();
    server.start();
    Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

    server.awaitTermination();
  }
}
