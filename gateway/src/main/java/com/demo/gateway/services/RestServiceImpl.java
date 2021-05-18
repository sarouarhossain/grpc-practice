package com.demo.gateway.services;

import com.demo.gateway.models.SquareResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class RestServiceImpl implements RestService {
  //private static final String API_BASE = "http://207.148.98.248:7775/api/square/";
  private static final String API_BASE = "http://localhost:7775/api/square/";

  private WebClient webClient = WebClient.create(API_BASE);

  @Override
  public Flux<SquareResponse> getData(Long limit) {

    return Flux.fromStream(LongStream.range(1, limit).boxed())
        .parallel()
        .flatMap(this::get)
        .ordered((u1, u2) -> (int) (u1.getNumber() - u2.getNumber()));
  }

  private Mono<SquareResponse> get(Long number) {
    return webClient.get().uri("{number}", number).retrieve().bodyToMono(SquareResponse.class);
  }

  @Override
  public CompletionStage<List<SquareResponse>> getDataRes(Long limit) throws URISyntaxException {
    HttpClient client = HttpClient.newHttpClient();
    List<URI> targets = new ArrayList<>();
    for (long key=1 ; key<=limit; key++) {
      targets.add(new URI(API_BASE + key));
    }
    System.out.println("TEST: "+targets);
    List<CompletableFuture<String>> futures =
        targets.stream()
            .map(
                target -> client
                    .sendAsync(
                        HttpRequest.newBuilder(target).GET().build(),
                        HttpResponse.BodyHandlers.ofString()).thenApplyAsync(HttpResponse::body))
            .collect(Collectors.toList());

    var allFutures =
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

    ObjectMapper mapper = new ObjectMapper();
    return allFutures.thenApplyAsync(
        v -> futures.stream().map(CompletableFuture::join).map(y->{
          try {
            return mapper.readValue(y,SquareResponse.class);
          } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
          }
        }).collect(Collectors.toList()));

  }

  @Override
  public List<SquareResponse> getDataNaive(Long limit) {
    List<SquareResponse> squareResponses = new ArrayList<>();
    HttpClient httpClient = HttpClient.newHttpClient();
    LongStream.range(1, limit)
        .forEach(
            x -> {
              try {
                var res =
                    httpClient.send(
                        HttpRequest.newBuilder(new URI(API_BASE + x)).GET().build(),
                        HttpResponse.BodyHandlers.ofString());
                ObjectMapper mapper = new ObjectMapper();
                SquareResponse squareResponse = mapper.readValue(res.body(), SquareResponse.class);
                squareResponses.add(squareResponse);
              } catch (IOException | InterruptedException | URISyntaxException e) {
                e.printStackTrace();
              }
            });
    return squareResponses;
  }
}
