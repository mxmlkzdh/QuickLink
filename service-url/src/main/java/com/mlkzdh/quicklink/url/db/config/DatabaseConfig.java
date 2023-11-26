package com.mlkzdh.quicklink.url.db.config;

public final class DatabaseConfig {

  public static final class Table {

    public static final class UrlRecords {

      public static final String NAME = "url_records";

      public static final class Column {
        public static final String ID = "id";
        public static final String DESTINATION = "destination";
        public static final String TIMESTAMP = "timestamp";
      }

    }

  }

}
