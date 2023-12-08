package nyc.hazelnut.quicklink.url.controller;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import nyc.hazelnut.quicklink.url.controller.model.UrlRequest;
import nyc.hazelnut.quicklink.url.controller.model.UrlResponse;
import nyc.hazelnut.quicklink.url.db.model.UrlRecord;
import nyc.hazelnut.quicklink.url.service.UrlService;
import nyc.hazelnut.quicklink.util.Convertor;
import nyc.hazelnut.quicklink.util.ShortUrl;

@RequestMapping("/api/v1")
@RestController
public class UrlController {

  @Value("${quicklink.url.shortUrl.base}")
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
   */
  @PostMapping(value = "/url", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UrlResponse> save(@Valid @RequestBody UrlRequest urlRequest) {
    // Persist
    UrlRecord savedUrlRecord = urlService.save(
        new UrlRecord.Builder()
            .destination(urlRequest.getDestination())
            .build());
    // Respond
    String key = Convertor.key(savedUrlRecord.getId());
    UrlResponse urlResponse = new UrlResponse.Builder()
        .key(key)
        .shortUrl(ShortUrl.build(baseUrl, "r", key))
        .destination(savedUrlRecord.getDestination())
        .build();
    return new ResponseEntity<>(urlResponse, HttpStatus.CREATED);
  }

  /**
   * Returns the {@link UrlRecord} in the database based on its key.
   * 
   * @param key The key associated with the {@link UrlRecord}
   * @return The response that contains the {@link UrlRecord}
   * @throws ResponseStatusException When the key is not present in the database
   */
  @GetMapping(value = "/url/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UrlRecord> find(
      @PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{6}$") String key)
      throws ResponseStatusException {
    // Look up
    Optional<UrlRecord> urlRecord = urlService.find(Convertor.id(key));
    if (urlRecord.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    // Respond
    return new ResponseEntity<>(urlRecord.get(), HttpStatus.OK);
  }

}
