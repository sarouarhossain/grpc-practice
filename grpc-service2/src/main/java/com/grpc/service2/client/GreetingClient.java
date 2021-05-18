package com.grpc.service2.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
  public static void main(String[] args) {
    //
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    GreetServiceGrpc.GreetServiceBlockingStub greetClient =
        GreetServiceGrpc.newBlockingStub(channel);
    Greeting greeting = Greeting.newBuilder().setFirstName("Sarouar").setLastName("Roman").build();
    GreetRequest request = GreetRequest.newBuilder().setGreeting(greeting).build();
    GreetResponse response = greetClient.greet(request);

    System.out.println(response.getResult());

    channel.shutdown();
  }
}
