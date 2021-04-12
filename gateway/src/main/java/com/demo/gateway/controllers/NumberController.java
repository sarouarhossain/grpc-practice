package com.demo.gateway.controllers;

import com.demo.gateway.models.SquareResponse;
import com.demo.gateway.services.RestService;
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
  private RestService restService;

  NumberController(RestService restService) {
    this.restService = restService;
  }

  @GetMapping("/restNaive/{limit}")
  public ResponseEntity<List<SquareResponse>> getDataNaive(@PathVariable Long limit) {
    var res = restService.getDataNaive(limit);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  @GetMapping("/rest/{limit}")
  public Flux<SquareResponse> getRestData(@PathVariable Long limit) {
    return restService.getData(limit);
  }

  @GetMapping("/unary/{limit}")
  public ResponseEntity<List<SquareResponse>> getUnaryData(@PathVariable String limit) {
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GetMapping("/stream/{limit}")
  public ResponseEntity<List<SquareResponse>> getStreamData(@PathVariable String limit) {
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
