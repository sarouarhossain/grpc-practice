package com.grpc.service2.server.services;

public class TestService {
  public int test(int n1, int n2) {
    System.out.println("Object ID of test: " + this.hashCode());
    return n1 + n2;
  }
}
