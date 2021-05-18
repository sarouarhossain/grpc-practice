package com.demo.gateway.controllers;

import com.demo.gateway.models.SquareResponse;
import com.demo.gateway.services.RestService;
import com.demo.gateway.services.RpcServiceImpl;
import java.net.URISyntaxException;
import java.util.concurrent.CompletionStage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NumberController {
  private final RestService restService;
  private final RpcServiceImpl rpcService;

  NumberController(RestService restService, RpcServiceImpl rpcService) {
    this.restService = restService;
    this.rpcService = rpcService;
  }

  @GetMapping("/rest/{limit}")
  public Flux<SquareResponse> getRestData(@PathVariable Long limit) {
    return restService.getData(limit);
  }

  @GetMapping("/rest2/{limit}")
  public CompletionStage<List<SquareResponse>> getRestData2(@PathVariable Long limit)
      throws URISyntaxException {
    return restService.getDataRes(limit);
  }

  @GetMapping("/unary/{limit}")
  public ResponseEntity<List<SquareResponse>> getUnaryData(@PathVariable Long limit) {
    var res = rpcService.getData(limit);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/stream/{limit}")
  public ResponseEntity<List<SquareResponse>> getStreamData(@PathVariable Long limit)
      throws InterruptedException {

    System.out.println("Request Came11...");
    var res = rpcService.getDataStream(limit);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/restNaive/{limit}")
  public ResponseEntity<List<SquareResponse>> getDataNaive(@PathVariable Long limit) {
    var res = restService.getDataNaive(limit);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }
}
