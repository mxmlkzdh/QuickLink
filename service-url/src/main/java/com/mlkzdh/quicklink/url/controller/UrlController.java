package com.mlkzdh.quicklink.url.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import jakarta.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class UrlController {

  @Value("${com.mlkzdh.quicklink.url.config.short-url.base}")
  private String baseUrl;

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
  public ResponseEntity<UrlResponse> save(@Valid @RequestBody UrlRequest urlRequest)
      throws ResponseStatusException {
    // Persistence
    UrlRecord savedUrlRecord = urlService.save(
        new UrlRecord.Builder()
            .destination(urlRequest.getDestination())
            .build());
    // Response
    String key = Base62.fromBase10(savedUrlRecord.getId());
    UrlResponse urlResponse = new UrlResponse.Builder()
        .key(key)
        .shortUrl(String.format("%s/%s", baseUrl, key))
        .destination(savedUrlRecord.getDestination())
        .build();
    return new ResponseEntity<>(urlResponse, HttpStatus.CREATED);
  }

  /**
   * Looks up the {@link UrlRecord} in the database based on its id
   * 
   * @param id The id associated with the {@link UrlRecord}
   * @return The response that contains the {@link UrlRecord}
   * @throws ResponseStatusException When the key is not present in the database
   */
  @GetMapping(value = "/url/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UrlRecord> find(@PathVariable Long id) throws ResponseStatusException {
    // Lookup
    Optional<UrlRecord> urlRecord = urlService.find(id);
    if (urlRecord.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    // Response
    return new ResponseEntity<>(urlRecord.get(), HttpStatus.OK);
  }

}
