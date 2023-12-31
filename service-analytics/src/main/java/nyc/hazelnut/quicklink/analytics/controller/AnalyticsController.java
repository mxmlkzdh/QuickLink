package nyc.hazelnut.quicklink.analytics.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.constraints.Pattern;
import nyc.hazelnut.quicklink.analytics.controller.model.AnalyticsResponse;
import nyc.hazelnut.quicklink.analytics.controller.model.HitRecord;
import nyc.hazelnut.quicklink.analytics.controller.model.UrlRecord;
import nyc.hazelnut.quicklink.analytics.service.AnalyticsService;

@RequestMapping("/api/v1")
@RestController
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  @Autowired
  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
  }

  /**
   * Returns the {@link UrlRecord} and its list of {@link HitRecord}s.
   * 
   * @param key The key associated with the destination URL
   * @return The response that contains the {@link AnalyticsResponse} object
   * @throws ResponseStatusException When the key does not exist in the database
   */
  @GetMapping(value = "/analytics/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AnalyticsResponse> analytics(
      @PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{6}$") String key)
      throws ResponseStatusException {
    // Look Up URL Service
    UrlRecord urlRecord = analyticsService.findUrlRecord(key);
    // Look Up Redirect Service
    List<HitRecord> hitRecords = analyticsService.findHitRecords(key);
    // Respond
    return new ResponseEntity<>(new AnalyticsResponse(urlRecord, hitRecords), HttpStatus.OK);
  }

}
