package com.mlkzdh.quicklink.analytics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.mlkzdh.quicklink.analytics.controller.AnalyticsController;
import com.mlkzdh.quicklink.analytics.service.AnalyticsService;

@SpringBootTest
class AnalyticsServiceApplicationTests {

  @Autowired
  private AnalyticsController analyticsController;

  @Autowired
  private AnalyticsService analyticsService;

  @Test
  void contextLoads() {
    assertNotNull(analyticsController);
    assertNotNull(analyticsService);
  }

}
