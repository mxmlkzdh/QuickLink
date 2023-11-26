package com.mlkzdh.quicklink.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public final class UrlUtils {

  private UrlUtils() {}

  public static boolean isValid(String url) {
    if (url == null || url.isEmpty()) {
      return false;
    }
    try {
      new URI(url).toURL();
      return true;
    } catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
      return false;
    }
  }

}
