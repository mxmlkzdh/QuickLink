package nyc.hazelnut.quicklink.redirect.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;

@EnableCaching
@Configuration
public class CacheConfig {

  public static final String CACHE_URL_RECORDS = "cache-url-records";

  private static final int MAXIMUM_SIZE = 100;

  @Bean
  public Caffeine<Object, Object> caffeineConfig() {
    return Caffeine.newBuilder().maximumSize(MAXIMUM_SIZE);
  }

  @Bean
  public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(CACHE_URL_RECORDS);
    caffeineCacheManager.setCaffeine(caffeine);
    return caffeineCacheManager;
  }

}
