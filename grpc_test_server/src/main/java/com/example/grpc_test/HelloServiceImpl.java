package com.example.grpc_test;

import com.example.grpc_test.proto.HelloRequest;
import com.example.grpc_test.proto.HelloResponse;
import com.example.grpc_test.proto.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceImplBase {

  @Override
  public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

    String greeting = request.getFirstName() + "," + request.getLastName();

    HelloResponse response = HelloResponse.newBuilder()
        .setGreeting(greeting)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  @Override
  public void helloServerStream(HelloRequest request,
      StreamObserver<HelloResponse> responseObserver) {

    List<String> greetingList = new ArrayList<>();
    for (int i = 1; i <= 3; i++) {
      greetingList.add(request.getFirstName() + "," + request.getLastName() + ":" + i);
    }

    for (String greeting : greetingList) {
      HelloResponse response = HelloResponse.newBuilder()
          .setGreeting(greeting)
          .build();
      responseObserver.onNext(response);
    }

    responseObserver.onCompleted();
  }

  /**
   *
   */
  public StreamObserver<com.example.grpc_test.proto.HelloRequest> helloClientStream(
      StreamObserver<HelloResponse> responseObserver) {
    return new StreamObserver<HelloRequest>() {
      @Override
      public void onNext(HelloRequest helloRequest) {
        System.out.println(helloRequest.getFirstName() + "," + helloRequest.getLastName());
        // 주의 : 여기서 responseObserver.onNext를 쓰면 biStream이 되버림
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("error");
      }

      @Override
      public void onCompleted() {
        responseObserver.onNext(HelloResponse.newBuilder().setGreeting("success").build());
        responseObserver.onCompleted();
      }
    };
  }

  /**
   *
   */
  public StreamObserver<HelloRequest> helloBiStream(
      StreamObserver<HelloResponse> responseObserver) {
    return new StreamObserver<HelloRequest>() {
      @Override
      public void onNext(HelloRequest helloRequest) {
        String greeting = helloRequest.getFirstName() + "," + helloRequest.getLastName();
        System.out.println(greeting);

        responseObserver.onNext(HelloResponse.newBuilder().setGreeting(greeting+"1").build());
        responseObserver.onNext(HelloResponse.newBuilder().setGreeting(greeting+"2").build());
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("error");
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}