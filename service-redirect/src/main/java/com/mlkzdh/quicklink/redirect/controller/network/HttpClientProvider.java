package com.mlkzdh.quicklink.redirect.controller.network;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public final class HttpClientProvider {

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new RedirectResponseErrorHandler());
    return restTemplate;
  }

}
