package nyc.hazelnut.quicklink.util;

public final class ShortUrl {

  private ShortUrl() {}

  public static String build(String baseUrl, String pathPrefix, String path) {
    return String.format("%s/%s/%s", baseUrl, pathPrefix, path);
  }

}
