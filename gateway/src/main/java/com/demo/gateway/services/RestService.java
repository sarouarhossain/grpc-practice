package com.demo.gateway.services;

import com.demo.gateway.models.SquareResponse;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RestService {
  public Flux<SquareResponse> getData(Long limit);

  public List<SquareResponse> getDataNaive(Long limit);
}
