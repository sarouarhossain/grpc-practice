package com.demo.gateway.services;

import com.demo.gateway.models.SquareResponse;
import java.net.URISyntaxException;
import java.util.concurrent.CompletionStage;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RestService {
  public Flux<SquareResponse> getData(Long limit);

  public List<SquareResponse> getDataNaive(Long limit);

  public CompletionStage<List<SquareResponse>> getDataRes(Long limit) throws URISyntaxException;
}
