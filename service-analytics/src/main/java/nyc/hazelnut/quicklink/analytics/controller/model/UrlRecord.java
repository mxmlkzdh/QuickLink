package nyc.hazelnut.quicklink.analytics.controller.model;

import java.util.Date;
import java.util.Locale;

public final class UrlRecord {

  private Long id;
  private String destination;
  private Date timestamp;

  public UrlRecord() {}

  private UrlRecord(Builder builder) {
    this.id = builder.id;
    this.destination = builder.destination;
    this.timestamp = builder.timestamp;
  }

  public Long getId() {
    return id;
  }

  public String getDestination() {
    return destination;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return String.format(Locale.getDefault(), "[id: %d, destination: %s, timestamp: %s]", id,
        destination, timestamp);
  }

  public static class Builder {

    private Long id;
    private String destination;
    private Date timestamp;

    public Builder() {}

    Builder(Long id, String destination, Date timestamp) {
      this.id = id;
      this.destination = destination;
      this.timestamp = timestamp;
    }

    public Builder id(Long id) {
      this.id = id;
      return Builder.this;
    }

    public Builder destination(String destination) {
      this.destination = destination;
      return Builder.this;
    }

    public Builder timestamp(Date timestamp) {
      this.timestamp = timestamp;
      return Builder.this;
    }

    public UrlRecord build() {
      return new UrlRecord(this);
    }

  }

}
