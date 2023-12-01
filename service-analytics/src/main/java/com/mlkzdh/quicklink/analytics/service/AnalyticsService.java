package com.mlkzdh.quicklink.analytics.service;

import java.util.Optional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.mlkzdh.quicklink.analytics.cache.CacheConfig;
import com.mlkzdh.quicklink.analytics.controller.model.UrlRecord;

@Service
public class AnalyticsService {

  private static final Log LOG = LogFactory.getLog(AnalyticsService.class);

  private final WebClient.Builder webClientBuilder;

  @Autowired
  public AnalyticsService(WebClient.Builder webClientBuilder) {
    this.webClientBuilder = webClientBuilder;
  }

  @Cacheable(cacheNames = CacheConfig.CACHE_URL_RECORDS, unless = "#result == null")
  public Optional<UrlRecord> findUrlRecord(Long id) {
    return Optional.empty();
  }

}
