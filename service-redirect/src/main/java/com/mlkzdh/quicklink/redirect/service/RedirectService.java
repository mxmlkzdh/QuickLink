package com.mlkzdh.quicklink.redirect.service;

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
import org.springframework.web.util.UriComponentsBuilder;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mlkzdh.quicklink.redirect.cache.CacheConfig;
import com.mlkzdh.quicklink.redirect.controller.model.UrlRecord;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;
import com.mlkzdh.quicklink.redirect.db.repository.HitRepository;
import reactor.core.publisher.Mono;

@Service
public class RedirectService {

  private static final Log LOG = LogFactory.getLog(RedirectService.class);

  @Value("${com.mlkzdh.quicklink.redirect.service-url.endpoint}")
  private String serviceUrlEndpoint;

  private final HitRepository hitRepository;
  private final WebClient.Builder webClientBuilder;

  @Autowired
  public RedirectService(HitRepository hitRepository, WebClient.Builder webClientBuilder) {
    this.hitRepository = hitRepository;
    this.webClientBuilder = webClientBuilder;
  }

  @Cacheable(CacheConfig.CACHE_URL_RECORDS)
  public Optional<UrlRecord> findUrlRecord(Long id) {
    return webClientBuilder.build()
        .get()
        .uri(UriComponentsBuilder.fromUriString(serviceUrlEndpoint)
            .path(String.valueOf(id))
            .build()
            .toUri())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(code -> code == HttpStatus.NOT_FOUND, e -> Mono.empty())
        .bodyToMono(UrlRecord.class)
        .blockOptional();
  }

  @CanIgnoreReturnValue
  public HitRecord save(HitRecord hitRecord) {
    HitRecord savedHitRecord = hitRepository.save(hitRecord);
    LOG.info(String.format("Hit saved: %s", savedHitRecord));
    return savedHitRecord;
  }

}
