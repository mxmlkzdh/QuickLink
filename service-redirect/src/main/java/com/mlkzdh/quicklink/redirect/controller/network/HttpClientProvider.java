package com.mlkzdh.quicklink.redirect.controller.network;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public final class HttpClientProvider {

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(3000);
    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
    restTemplate.setErrorHandler(new RedirectResponseErrorHandler());
    return restTemplate;
  }

}
