package com.mlkzdh.quicklink.redirect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.mlkzdh.quicklink.redirect.cache.CacheConfig;
import com.mlkzdh.quicklink.redirect.controller.model.UrlRecord;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;
import com.mlkzdh.quicklink.redirect.db.repository.HitRepository;

@Service
public class RedirectService {

  @Value("${com.mlkzdh.quicklink.redirect.serviceUrl.endpoint}")
  private String serviceUrlEndpoint;

  private final HitRepository hitRepository;
  private final RestTemplate restTemplate;

  @Autowired
  public RedirectService(HitRepository hitRepository, RestTemplate restTemplate) {
    this.hitRepository = hitRepository;
    this.restTemplate = restTemplate;
  }

  @Cacheable(CacheConfig.CACHE_URL_RECORDS)
  public UrlRecord findUrlRecord(Long id) throws ResponseStatusException {
    return restTemplate.getForObject(
        UriComponentsBuilder.fromUriString(serviceUrlEndpoint)
            .path(String.valueOf(id))
            .build()
            .toUri(),
        UrlRecord.class);
  }

  public HitRecord save(HitRecord hitRecord) {
    return hitRepository.save(hitRecord);
  }

}
