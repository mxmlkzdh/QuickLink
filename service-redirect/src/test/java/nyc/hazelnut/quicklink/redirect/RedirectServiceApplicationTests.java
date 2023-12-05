package nyc.hazelnut.quicklink.redirect;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import nyc.hazelnut.quicklink.redirect.controller.RedirectController;
import nyc.hazelnut.quicklink.redirect.service.RedirectService;

@SpringBootTest
class RedirectServiceApplicationTests {

  @Autowired
  private RedirectController redirectController;

  @Autowired
  private RedirectService redirectService;

  @Test
  void contextLoads() {
    assertNotNull(redirectController);
    assertNotNull(redirectService);
  }

}
