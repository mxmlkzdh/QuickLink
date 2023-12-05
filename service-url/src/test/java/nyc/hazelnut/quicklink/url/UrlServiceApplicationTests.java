package nyc.hazelnut.quicklink.url;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import nyc.hazelnut.quicklink.url.controller.UrlController;
import nyc.hazelnut.quicklink.url.service.UrlService;

@SpringBootTest
class UrlServiceApplicationTests {

  @Autowired
  private UrlController urlController;

  @Autowired
  private UrlService urlService;

  @Test
  void contextLoads() {
    assertNotNull(urlController);
    assertNotNull(urlService);
  }

}
