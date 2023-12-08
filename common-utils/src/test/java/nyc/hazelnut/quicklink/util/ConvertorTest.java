package nyc.hazelnut.quicklink.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ConvertorTest {

  @Test
  public void key() {
    assertEquals("abvxB4", Convertor.key(19871366));
  }

  @Test
  public void key_handleZero() {
    assertEquals("aaaaaa", Convertor.key(0));
  }

  @Test
  public void id() {
    assertEquals(15342917, Convertor.id("abcxyz"));
  }

  @Test
  public void id_handle999999() {
    assertEquals(56800235583L, Convertor.id("999999"));
  }

}
