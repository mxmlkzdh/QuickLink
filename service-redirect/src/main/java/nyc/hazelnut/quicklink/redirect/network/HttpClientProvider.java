package nyc.hazelnut.quicklink.redirect.network;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpClientProvider {

  public static final long TIMEOUT_MILLIS = 5000;

  @LoadBalanced
  @Bean
  public WebClient.Builder restTemplate() {
    return WebClient.builder();
  }

}
