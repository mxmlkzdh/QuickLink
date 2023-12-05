package nyc.hazelnut.quicklink.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Base62Test {

  @Test
  public void fromBase10() {
    assertEquals("abvxB4", Base62.fromBase10(19871366));
  }

  @Test
  public void fromBase10_handleZero() {
    assertEquals("aaaaaa", Base62.fromBase10(0));
  }

  @Test
  public void toBase10() {
    assertEquals(15342917, Base62.toBase10("abcxyz"));
  }

  @Test
  public void toBase10_handle999999() {
    assertEquals(56800235583L, Base62.toBase10("999999"));
  }

}
