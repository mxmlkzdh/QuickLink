package com.mlkzdh.quicklink.redirect.controller;

import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import com.mlkzdh.quicklink.redirect.controller.model.UrlRecord;
import com.mlkzdh.quicklink.redirect.db.model.HitRecord;
import com.mlkzdh.quicklink.redirect.service.RedirectService;
import com.mlkzdh.quicklink.util.Base62;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/u")
@RestController
public final class RedirectController {

  private static final Log LOG = LogFactory.getLog(RedirectController.class);

  @Value("${com.mlkzdh.quicklink.redirect.service.url.endpoint}")
  private String serviceUrlEndpoint;

  private final RedirectService redirectService;
  private final RestTemplate restTemplate;

  @Autowired
  public RedirectController(RedirectService redirectService, RestTemplate restTemplate) {
    this.redirectService = redirectService;
    this.restTemplate = restTemplate;
  }

  /**
   * Looks up the destination URL for the given key, saves the hit record in the database, and
   * redirects to the destination URL
   * 
   * @param key The key associated wit the destination URL
   * @param request The original HTTP request
   * @return The HTTP response that redirects to the destination URL
   * @throws ResponseStatusException When the key does not exist in the database
   */
  @SuppressWarnings("all")
  @GetMapping("/{key}")
  public ResponseEntity<Void> redirect(@PathVariable String key, HttpServletRequest request)
      throws ResponseStatusException {
    // Validation
    // Lookup
    UrlRecord urlRecord =
        restTemplate.getForObject(
            UriComponentsBuilder.fromUriString(serviceUrlEndpoint)
                .path(String.valueOf(Base62.toBase10(key)))
                .build()
                .toUri(),
            UrlRecord.class);
    // Persistence
    HitRecord savedHitRecord = redirectService.save(new HitRecord.Builder()
        .urlRecordId(urlRecord.getId())
        .ip(request.getRemoteAddr())
        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
        .referer(request.getHeader(HttpHeaders.REFERER))
        .build());
    LOG.info(String.format("Hit saved: %s", savedHitRecord));
    // Response
    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("no-cache");
    headers.setLocation(URI.create(urlRecord.getDestination()));
    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
  }

}
