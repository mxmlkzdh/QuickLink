package com.mlkzdh.quicklink.analytics.service;

import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.mlkzdh.quicklink.analytics.cache.CacheConfig;
import com.mlkzdh.quicklink.analytics.controller.model.HitRecord;
import com.mlkzdh.quicklink.analytics.controller.model.UrlRecord;
import reactor.core.publisher.Mono;

@Service
public class AnalyticsService {

  private static final Log LOG = LogFactory.getLog(AnalyticsService.class);

  @Value("${com.mlkzdh.quicklink.analytics.service-url.endpoint}")
  private String serviceUrlEndpoint;

  private final WebClient.Builder webClientBuilder;

  @Autowired
  public AnalyticsService(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @Cacheable(cacheNames = CacheConfig.CACHE_URL_RECORDS, unless = "#result == null")
  public Optional<UrlRecord> fetchUrlRecord(Long id) {
    return webClientBuilder.build()
        .get()
        .uri(UriComponentsBuilder.fromUriString(serviceUrlEndpoint)
            .path(String.valueOf(id))
            .build()
            .toUri())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(code -> code == HttpStatus.NOT_FOUND,
            e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
        .bodyToMono(UrlRecord.class)
        .doOnSuccess(urlRecord -> LOG.info(String.format("UrlRecord fetched: %d", id)))
        .onErrorResume(ResponseStatusException.class, e -> Mono.empty())
        .blockOptional();
  }

  public Optional<HitRecord> fetchHitRecords(Long id) {
    return Optional.empty();
  }

}
