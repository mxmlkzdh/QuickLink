package nyc.hazelnut.quicklink.redirect.db.config;

public final class DatabaseConfig {

  public static final class Table {

    public static final class HitRecords {

      public static final String NAME = "hit_records";

      public static final class Column {
        public static final String ID = "id";
        public static final String URL_RECORD_ID = "url_record_id";
        public static final String IP = "ip";
        public static final String USER_AGENT = "user_agent";
        public static final String REFERER = "referer";
        public static final String TIMESTAMP = "timestamp";
      }

    }

  }

}
