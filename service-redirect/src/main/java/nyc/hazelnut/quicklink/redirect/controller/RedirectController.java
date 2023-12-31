package nyc.hazelnut.quicklink.redirect.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import nyc.hazelnut.quicklink.redirect.controller.model.HitRecordsResponse;
import nyc.hazelnut.quicklink.redirect.controller.model.UrlRecord;
import nyc.hazelnut.quicklink.redirect.db.model.HitRecord;
import nyc.hazelnut.quicklink.redirect.service.RedirectService;
import nyc.hazelnut.quicklink.util.Convertor;

@RestController
public class RedirectController {

  private static final String HEADER_CACHE_CONTROL = "no-cache, no-store";
  private static final String HEADER_X_REAL_IP = "X-Real-IP";
  private final RedirectService redirectService;

  @Autowired
  public RedirectController(RedirectService redirectService) {
    this.redirectService = redirectService;
  }

  /**
   * Looks up the destination URL for the given key, saves the {@link HitRecord} in the database,
   * and redirects to the destination URL.
   * 
   * @param key The key associated with the destination URL
   * @param request The original HTTP request
   * @return The HTTP response that redirects to the destination URL
   * @throws ResponseStatusException When the key does not exist in the database
   */
  @GetMapping("/r/{key}")
  public ResponseEntity<Void> redirect(
      @PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{6}$") String key, HttpServletRequest request)
      throws ResponseStatusException {
    // Look up
    UrlRecord urlRecord = redirectService.findUrlRecord(key);
    // Persist
    HitRecord hitRecord = buildHitRecord(urlRecord, request);
    redirectService.save(hitRecord);
    // Respond
    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl(HEADER_CACHE_CONTROL);
    headers.setLocation(URI.create(urlRecord.getDestination()));
    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
  }

  /**
   * Returns all the {@link HitRecord}s in the database based on their destination URL's key.
   * 
   * @param key The key associated with the destination URL
   * @return The response that contains the list of {@link HitRecord}s
   */
  @GetMapping(value = "/api/v1/hits/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HitRecordsResponse> getHitRecords(
      @PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{6}$") String key) {
    // Look up
    Optional<List<HitRecord>> hitRecords =
        redirectService.findAllByUrlRecordId(Convertor.id(key));
    // Respond
    if (hitRecords.isEmpty()) {
      return new ResponseEntity<>(HitRecordsResponse.empty(), HttpStatus.OK);
    }
    return new ResponseEntity<>(new HitRecordsResponse(hitRecords.get()), HttpStatus.OK);
  }

  private static HitRecord buildHitRecord(UrlRecord urlRecord,
      HttpServletRequest request) {
    return new HitRecord.Builder()
        .urlRecordId(urlRecord.getId())
        .ip(request.getHeader(HEADER_X_REAL_IP))
        .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
        .referer(request.getHeader(HttpHeaders.REFERER))
        .build();
  }

}
