package com.mlkzdh.quicklink.analytics.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.mlkzdh.quicklink.analytics.controller.model.AnalyticsResponse;
import com.mlkzdh.quicklink.analytics.controller.model.HitRecord;
import com.mlkzdh.quicklink.analytics.controller.model.UrlRecord;
import com.mlkzdh.quicklink.analytics.service.AnalyticsService;
import jakarta.validation.constraints.Pattern;

@RequestMapping("/api/v1")
@RestController
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  @Autowired
  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
  }

  @GetMapping(value = "/analytics/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AnalyticsResponse> analytics(
      @PathVariable @Pattern(regexp = "^[a-zA-Z0-9]{6}$") String key)
      throws ResponseStatusException {
    // URL Service Lookup
    Optional<UrlRecord> urlRecord = analyticsService.findUrlRecord(key);
    if (urlRecord.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    // Redirect Service Lookup
    List<HitRecord> hitRecords = analyticsService.findHitRecords(key);
    // Response
    return new ResponseEntity<>(new AnalyticsResponse(urlRecord.get(), hitRecords),
        HttpStatus.OK);
  }

}
