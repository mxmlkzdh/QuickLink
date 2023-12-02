package com.mlkzdh.quicklink.analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mlkzdh.quicklink.analytics.service.AnalyticsService;

@RequestMapping("/api/v1")
@RestController
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  @Autowired
  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
  }

}
