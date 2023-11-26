package com.mlkzdh.quicklink.url.controller;

import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.mlkzdh.quicklink.url.controller.model.UrlRequest;
import com.mlkzdh.quicklink.url.controller.model.UrlResponse;
import com.mlkzdh.quicklink.url.db.model.UrlRecord;
import com.mlkzdh.quicklink.url.service.UrlService;
import com.mlkzdh.quicklink.util.Base62;

@RequestMapping("/api/v1")
@RestController
public final class UrlController {

  private static final Log LOG = LogFactory.getLog(UrlController.class);
  private final UrlService urlService;

  @Autowired
  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  /**
   * Stores the destination URL in the database and returns the {@link UrlResponse} that contains
   * the short URL.
   * 
   * @param urlRequest The request that contains the destination URL
   * @return The response that contains the short URL
   * @throws ResponseStatusException When the destination URL is not present in the request or is
   *         invalid
   */
  @PostMapping(value = "/url", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UrlResponse> save(@RequestBody UrlRequest urlRequest)
      throws ResponseStatusException {
    // Validation
    if (StringUtils.isBlank(urlRequest.getDestination())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The destination URL is not present.");
    }
    // Persistence
    UrlRecord savedUrlRecord = urlService.save(
        new UrlRecord.Builder()
            .destination(urlRequest.getDestination())
            .build());
    LOG.info(String.format("URL saved: %s", savedUrlRecord));
    // Response
    String key = Base62.fromBase10(savedUrlRecord.getId());
    UrlResponse urlResponse = new UrlResponse.Builder()
        .key(key)
        .shortUrl(String.format("http://mlkzdh.com/%s", key))
        .destination(savedUrlRecord.getDestination())
        .build();
    return new ResponseEntity<>(urlResponse, HttpStatus.CREATED);
  }

  /**
   * Looks up the {@link UrlRecord} in the database based on its key
   * 
   * @param key The key associated with the {@link UrlRecord}
   * @return The response that contains the {@link UrlRecord}
   * @throws ResponseStatusException When the key is not present in the request path
   */
  @GetMapping("/url/{key}")
  public ResponseEntity<UrlRecord> find(@PathVariable String key) throws ResponseStatusException {
    // Validation
    if (StringUtils.isBlank(key)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "The URL key is not present.");
    }
    // Lookup
    Optional<UrlRecord> urlRecord = urlService.find(Base62.toBase10(key));
    if (urlRecord.isEmpty()) {
      LOG.warn(String.format("URL not found: %s", key));
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    // Response
    return new ResponseEntity<>(urlRecord.get(), HttpStatus.OK);
  }

}
