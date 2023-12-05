package nyc.hazelnut.quicklink.analytics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import nyc.hazelnut.quicklink.analytics.controller.AnalyticsController;
import nyc.hazelnut.quicklink.analytics.service.AnalyticsService;

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
