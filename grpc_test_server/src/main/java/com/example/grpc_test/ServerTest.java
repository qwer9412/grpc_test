package com.example.grpc_test;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class ServerTest {

  public static void main(String[] args) throws IOException, InterruptedException {
    Server server = ServerBuilder
        .forPort(8081)
        .addService(new HelloServiceImpl()).build();

    server.start();
    server.awaitTermination();
  }
}
