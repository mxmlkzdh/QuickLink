package com.mlkzdh.quicklink.url.db.model;

import java.util.Date;
import java.util.Locale;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import com.mlkzdh.quicklink.url.db.config.DatabaseConfig.Table.UrlRecords;
import com.mlkzdh.quicklink.url.db.util.UniqueRandomIdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity(name = UrlRecords.NAME)
@Table(name = UrlRecords.NAME)
public final class UrlRecord {

  @Id
  @GenericGenerator(name = "URIDG", type = UniqueRandomIdGenerator.class)
  @GeneratedValue(generator = "URIDG")
  @Column(name = UrlRecords.Column.ID)
  private Long id;

  @Column(name = UrlRecords.Column.DESTINATION, length = 1024)
  private String destination;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(name = UrlRecords.Column.TIMESTAMP)
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
