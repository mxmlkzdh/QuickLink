package nyc.hazelnut.quicklink.util;

public final class Convertor {

  private Convertor() {}

  public static String key(long id) {
    return Base62.fromBase10(id);
  }

  public static long id(String key) {
    return Base62.toBase10(key);
  }

}
