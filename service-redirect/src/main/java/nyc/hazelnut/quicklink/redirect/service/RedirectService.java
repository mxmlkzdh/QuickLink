package nyc.hazelnut.quicklink.redirect.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
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
import nyc.hazelnut.quicklink.redirect.cache.CacheConfig;
import nyc.hazelnut.quicklink.redirect.controller.model.UrlRecord;
import nyc.hazelnut.quicklink.redirect.db.model.HitRecord;
import nyc.hazelnut.quicklink.redirect.db.repository.HitRepository;
import nyc.hazelnut.quicklink.redirect.network.HttpClientProvider;
import reactor.core.publisher.Mono;

@Service
public class RedirectService {

  private static final Log LOG = LogFactory.getLog(RedirectService.class);

  @Value("${quicklink.redirect.serviceUrl.endpoint}")
  private String serviceUrlEndpoint;

  private final HitRepository hitRepository;
  private final WebClient.Builder webClientBuilder;

  @Autowired
  public RedirectService(HitRepository hitRepository, WebClient.Builder webClientBuilder) {
    this.hitRepository = hitRepository;
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
        .timeout(Duration.ofMillis(HttpClientProvider.TIMEOUT_MILLIS))
        .onErrorResume(TimeoutException.class, e -> {
          throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT);
        })
        .block();
  }

  public HitRecord save(HitRecord hitRecord) {
    HitRecord savedHitRecord = hitRepository.save(hitRecord);
    LOG.info(String.format("HitRecord saved: %s", savedHitRecord));
    return savedHitRecord;
  }

  public Optional<List<HitRecord>> findAllByUrlRecordId(long urlRecordId) {
    return hitRepository.findAllByUrlRecordId(urlRecordId);
  }

}
