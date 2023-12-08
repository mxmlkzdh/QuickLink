package nyc.hazelnut.quicklink.analytics.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import nyc.hazelnut.quicklink.analytics.cache.CacheConfig;
import nyc.hazelnut.quicklink.analytics.controller.model.HitRecord;
import nyc.hazelnut.quicklink.analytics.controller.model.HitRecordsResponse;
import nyc.hazelnut.quicklink.analytics.controller.model.UrlRecord;
import reactor.core.publisher.Mono;

@Service
public class AnalyticsService {

  @Value("${quicklink.analytics.serviceUrl.endpoint}")
  private String serviceUrlEndpoint;

  @Value("${quicklink.analytics.serviceRedirect.endpoint}")
  private String serviceRedirectEndpoint;

  private final WebClient.Builder webClientBuilder;

  @Autowired
  public AnalyticsService(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @Cacheable(cacheNames = CacheConfig.CACHE_URL_RECORDS, unless = "#result == null")
  public UrlRecord findUrlRecord(String key) throws ResponseStatusException {
    return webClientBuilder.build()
        .get()
        .uri(UriComponentsBuilder.fromUriString(serviceUrlEndpoint)
            .path(key)
            .build()
            .toUri())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(code -> code == HttpStatus.NOT_FOUND,
            e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
        .bodyToMono(UrlRecord.class)
        .block();
  }

  public List<HitRecord> findHitRecords(String key) {
    return webClientBuilder.build()
        .get()
        .uri(UriComponentsBuilder.fromUriString(serviceRedirectEndpoint)
            .path(key)
            .build()
            .toUri())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(HitRecordsResponse.class)
        .map(HitRecordsResponse::getHitRecords)
        .block();
  }

}
