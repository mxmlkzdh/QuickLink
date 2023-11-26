package com.mlkzdh.quicklink.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class UrlUtilsTest {

  @Test
  public void isValid() throws Exception {
    assertFalse(UrlUtils.isValid(null));
    assertFalse(UrlUtils.isValid(""));
    assertTrue(UrlUtils.isValid("http://google.com/"));
    assertFalse(UrlUtils.isValid("https://www.google.com/ java-%%$^&& iuyi"));
  }

}
