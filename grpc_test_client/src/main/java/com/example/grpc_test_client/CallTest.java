package com.example.grpc_test_client;

import com.example.grpc_test.proto.HelloRequest;
import com.example.grpc_test.proto.HelloResponse;
import com.example.grpc_test.proto.HelloServiceGrpc;
import com.example.grpc_test.proto.HelloServiceGrpc.HelloServiceBlockingStub;
import com.example.grpc_test.proto.HelloServiceGrpc.HelloServiceFutureStub;
import com.example.grpc_test.proto.HelloServiceGrpc.HelloServiceStub;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallTest {

  public static void main(String[] args) {
    // channel, stub 준비
    GrpcCaller caller = new GrpcCaller("localhost", 9090);

    // Request들 준비
    List<HelloRequest> requestList = new ArrayList<>();
    requestList.add(HelloRequest.newBuilder().setFirstName("a").setLastName("aa").build());
    requestList.add(HelloRequest.newBuilder().setFirstName("b").setLastName("bb").build());
    requestList.add(HelloRequest.newBuilder().setFirstName("c").setLastName("cc").build());

    caller.sendBlockingUnary(requestList.get(0));
    caller.sendAsynUnary(requestList.get(0));
    caller.sendFutureUnary(requestList.get(0));
    caller.sendAsynServerStream(requestList.get(0));
    caller.sendBlockingServerStream(requestList.get(0));
    caller.sendAsynClientStream(requestList);
    caller.sendAsynBiStream(requestList);
  }
}
