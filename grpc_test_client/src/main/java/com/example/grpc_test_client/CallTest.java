package com.example.grpc_test_client;

import com.example.grpc_test.proto.HelloRequest;
import com.example.grpc_test.proto.HelloResponse;
import com.example.grpc_test.proto.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class CallTest {
  public void call() {
    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    HelloServiceGrpc.HelloServiceBlockingStub stub
        = HelloServiceGrpc.newBlockingStub(channel);

    HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
        .setFirstName("Baeldung")
        .setLastName("gRPC")
        .build());

    System.out.println(helloResponse.getGreeting());

    channel.shutdown();
  }
}
