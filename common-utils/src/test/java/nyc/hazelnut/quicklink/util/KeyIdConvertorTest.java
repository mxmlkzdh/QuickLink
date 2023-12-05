package nyc.hazelnut.quicklink.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class KeyIdConvertorTest {

  @Test
  public void key() {
    assertEquals("abvxB4", KeyIdConvertor.key(19871366));
  }

  @Test
  public void key_handleZero() {
    assertEquals("aaaaaa", KeyIdConvertor.key(0));
  }

  @Test
  public void id() {
    assertEquals(15342917, KeyIdConvertor.id("abcxyz"));
  }

  @Test
  public void id_handle999999() {
    assertEquals(56800235583L, KeyIdConvertor.id("999999"));
  }

}
