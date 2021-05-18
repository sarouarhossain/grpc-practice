package com.demo.gateway.services;

import com.demo.gateway.models.SquareResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        .runOn(Schedulers.boundedElastic())
        .flatMap(this::get)
        .ordered((u1, u2) -> (int) (u1.getNumber() - u2.getNumber()));
  }

  private Mono<SquareResponse> get(Long number) {
    return webClient.get().uri("{number}", number).retrieve().bodyToMono(SquareResponse.class);
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
