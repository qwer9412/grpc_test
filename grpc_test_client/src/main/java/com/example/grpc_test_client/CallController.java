package com.example.grpc_test_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {

  @Autowired
  CallTest callTest;

  @GetMapping("/test")
  public void test() {
    callTest.call();
  }
}
