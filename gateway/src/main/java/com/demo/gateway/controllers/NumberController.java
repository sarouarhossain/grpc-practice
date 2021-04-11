package com.demo.gateway.controllers;

import com.demo.gateway.models.SquareResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NumberController {

  @GetMapping("/rest/{limit}")
  public ResponseEntity<List<SquareResponse>> getRestData(@PathVariable String limit) {
    return new ResponseEntity<>(null, HttpStatus.OK);
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
